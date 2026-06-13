package epf.management.internal;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import com.stripe.StripeClient;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.SubscriptionCreateParams;
import epf.management.config.util.ConfigPath;
import epf.management.payment.schema.Customer;
import epf.management.payment.schema.Product;
import epf.management.payment.schema.Subscription;
import epf.naming.Naming;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PaymentManagement {
	
	@Inject
	@ConfigProperty(name = Naming.Payment.PRODUCT_ID)
	String productId;
	
	@Inject
	@ConfigProperty(name = Naming.Payment.TRIAL_PERIOD_DAYS)
	Integer trialPeriodDays;
	
	private final ConfigPath config = new ConfigPath("/epf/config/payment");
	
	private StripeClient getClient() {
		final String paymentSecret = config.getValue(Naming.Management.Internal.PAYMENT_MANAGEMENT_SECRET);
		final StripeClient client = new StripeClient(paymentSecret);
		return client;
	}
	
	public Product getDefaultProduct() throws Exception {
		final Product defaultProduct = new Product();
		final StripeClient client = getClient();
		final com.stripe.model.Product product = client.v1().products().retrieve(productId);
		defaultProduct.setId(product.getId());
		defaultProduct.setDefault_price_id(product.getDefaultPrice());
		defaultProduct.setDescription(product.getDescription());
		defaultProduct.setName(product.getName());
		defaultProduct.setUrl(product.getUrl());
		return defaultProduct;
	}

	public Customer createCustomer(final String email, final String name) throws Exception {
		final StripeClient client = getClient();
		final CustomerCreateParams params = CustomerCreateParams.builder()
				.setName(name)
				.setEmail(email)
				.build();
		final com.stripe.model.Customer customer = client.v1().customers().create(params);
		final Customer newCustomer = new Customer();
		newCustomer.setEmail(email);
		newCustomer.setId(customer.getId());
		newCustomer.setName(customer.getName());
		return newCustomer;
	}
	
	public Subscription createTrialSubscription(final String customerId, final String priceId) throws Exception {
		final Subscription trialSubscription = new Subscription();
		final StripeClient client = getClient();
		final SubscriptionCreateParams params = SubscriptionCreateParams.builder()
				    .setCustomer(customerId)
				    .addItem(
				      SubscriptionCreateParams.Item.builder().setPrice(priceId).build()
				    )
				    .setTrialPeriodDays(trialPeriodDays.longValue())
				    .build();
		final com.stripe.model.Subscription subscription = client.v1().subscriptions().create(params);
		trialSubscription.setCustomer_id(subscription.getCustomer());
		trialSubscription.setDescription(subscription.getDescription());
		trialSubscription.setId(subscription.getId());
		return trialSubscription;
	}
}
