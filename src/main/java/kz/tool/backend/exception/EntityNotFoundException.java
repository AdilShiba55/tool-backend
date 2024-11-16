package kz.tool.backend.exception;

public class EntityNotFoundException extends ToolException {

    public EntityNotFoundException(Class<?> clazz, Object id) {
        super("Запись " + clazz.getCanonicalName() + " с идентификатором " + id + " не найдена");
    }
}
