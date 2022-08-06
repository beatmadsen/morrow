package morrow;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import morrow.config.ManagedSingleton;
import morrow.config.SingletonStore;

import java.util.Set;

public final class MyValidation extends ManagedSingleton {

    private final ValidatorFactory factory;

    public MyValidation(SingletonStore singletonStore) {
        super(singletonStore);
        factory = Validation.buildDefaultValidatorFactory();

    }

    public <T> Set<ConstraintViolation<T>> validate(T object) {
        Validator validator = factory.getValidator();
        return validator.validate(object);
    }

    @Override
    public void close() throws Exception {
        factory.close();
    }
}
