package gr.aueb.cf.schoolapppro.service.exception;

public class EntityNotFoundException extends Exception {

    private static final Long serialVersionUID = 123564L;

    public EntityNotFoundException(Class<?> clazz, Long id) {
        super("Entity " + clazz.getSimpleName() + " with id " + id + " does not exist.");
    }
}
