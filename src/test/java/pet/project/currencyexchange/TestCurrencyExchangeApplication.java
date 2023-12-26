package pet.project.currencyexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestCurrencyExchangeApplication {

    public static void main(String[] args) {
        SpringApplication.from(CurrencyExchangeApplication::main).with(TestCurrencyExchangeApplication.class).run(args);
    }

}
