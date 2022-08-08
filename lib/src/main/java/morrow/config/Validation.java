package morrow.config;

import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

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

    @Override
    public void close() throws Exception {
        factory.close();
    }
}
