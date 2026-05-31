package epf.management.internal;

import com.stripe.StripeClient;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import epf.management.config.util.ConfigPath;
import epf.naming.Naming;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentManagement {
	
	private final ConfigPath config = new ConfigPath("/epf/config/payment");

	public String createCustomer(final String email, final String name) throws Exception {
		final String paymentSecret = config.getValue(Naming.Management.Internal.PAYMENT_MANAGEMENT_SECRET);
		final StripeClient client = new StripeClient(paymentSecret);
		final CustomerCreateParams params = CustomerCreateParams.builder()
				.setName(name)
				.setEmail(email)
				.build();
		final Customer customer = client.v1().customers().create(params);
		return customer.getId();
	}
}
