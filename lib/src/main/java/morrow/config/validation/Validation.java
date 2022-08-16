package morrow.config.validation;

import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import morrow.config.singleton.ManagedSingleton;
import morrow.config.singleton.SingletonStore;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.util.stream.Collectors;

public class Validation extends ManagedSingleton {

    private final ValidatorFactory factory;

    public Validation(SingletonStore singletonStore) {
        super(singletonStore);
        factory = jakarta.validation.Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();

    }

    public Validator validator() {
        return factory.getValidator();
    }

    public void validateConfig(Object o) throws ConfigurationValidationException {
        var violations = validator().validate(o);
        if (!violations.isEmpty()) {
            var message = "Invalid configuration: " +
                    violations.stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(", "));
            throw new ConfigurationValidationException(message);
        }
    }


    @Override
    public void close() throws Exception {
        factory.close();
    }
}
