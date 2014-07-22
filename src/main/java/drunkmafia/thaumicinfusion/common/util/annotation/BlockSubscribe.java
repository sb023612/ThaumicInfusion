package drunkmafia.thaumicinfusion.common.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by DrunkMafia on 02/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BlockSubscribe {
}
