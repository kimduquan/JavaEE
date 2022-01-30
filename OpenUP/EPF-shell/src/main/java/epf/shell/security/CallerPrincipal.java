package epf.shell.security;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = IdentityStore.class)
@Documented
@Retention(RUNTIME)
@Target(PARAMETER)
/**
 * @author PC
 *
 */
public @interface CallerPrincipal {
	
	String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
