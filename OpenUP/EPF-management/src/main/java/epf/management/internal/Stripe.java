package epf.management.internal;

import io.quarkus.runtime.annotations.RegisterForReflection;
import com.stripe.model.Product;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;

@RegisterForReflection(targets = {
		Product.class,
		Customer.class,
		Subscription.class
})
public class Stripe {

}
