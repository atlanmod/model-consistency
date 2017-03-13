package fr.inria.atlanmod.consistency;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by sunye on 17/02/2017.
 */
public class SharedResourceFactory implements Resource.Factory {
    /**
     * Constructs a new {@code PersistentResourceFactory}.
     */
    protected SharedResourceFactory() {
    }

    /**
     * Returns the instance of this class.
     *
     * @return the instance of this class
     */
    @Nonnull
    public static SharedResourceFactory getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * {@inheritDoc}
     * <p>
     *
     */
    @Nullable
    @Override
    public Resource createResource(@Nonnull URI uri) {
        checkNotNull(uri);
        return new SharedResource(uri);
    }

    /**
     * The initialization-on-demand holder of the singleton of this class.
     */
    private static class Holder {

        /**
         * The instance of the outer class.
         */
        private static final SharedResourceFactory INSTANCE = new SharedResourceFactory();
    }
}
