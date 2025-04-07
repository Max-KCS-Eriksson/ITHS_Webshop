package com.maxeriksson.iths.Webshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.maxeriksson.iths.Webshop.domain.order.OrderLine;
import com.maxeriksson.iths.Webshop.domain.product.Category;
import com.maxeriksson.iths.Webshop.domain.product.Product;
import com.maxeriksson.iths.Webshop.domain.user.User;
import com.maxeriksson.iths.Webshop.repository.order.OrderRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
public class ShoppingBasketServiceTest {

    @Mock private OrderRepository orderRepository;

    @InjectMocks private static ShoppingBasketService service;

    // Set up

    private static Product[] products;

    @BeforeAll
    public static void setUp() {
        Category category = new Category("Category");
        products =
                new Product[] {
                    new Product("Foo", 10, category),
                    new Product("Bar", 20, category),
                    new Product("Fizz", 30, category),
                    new Product("Buzz", 40, category)
                };

        // Manual mocking for Spring Security context
        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        User mockUser = new User("email@domain.io", "verySecuryPassword");

        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(mockUser);
        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
    }

    private void ensureShoppingBasketNotEmpty(int initialContentSize) {
        if (initialContentSize == 0) {
            for (Product product : products) {
                service.addProduct(new OrderLine(product, 1));
            }
        }

        if (service.getAllProducts().size() == 0) {
            fail(
                    "Couldn't be properly tested due to an empty Shopping Basket: Caused by"
                            + " ShoppingBasketService.addProduct(OrderLine orderLine)");
        }
    }

    // Unit tests

    @Test
    public void addProductTest() {
        for (Product product : products) {
            service.addProduct(new OrderLine(product, 1));
        }

        int expected = products.length;
        int actual = service.getAllProducts().size();
        assertEquals(expected, actual);
    }

    @Test
    public void noDuplicateProductsTest() {
        for (Product product : products) {
            service.addProduct(new OrderLine(product, 1));
        }
        int expected = service.getAllProducts().size();

        for (Product product : products) {
            service.addProduct(new OrderLine(product, 2));
        }
        int actual = service.getAllProducts().size();

        assertEquals(expected, actual);
    }

    @Test
    public void removeProductTest() {
        int initialContentSize = service.getAllProducts().size();
        ensureShoppingBasketNotEmpty(initialContentSize);

        for (Product product : products) {
            service.removeProduct(product);
        }

        int expected = 0;
        int actual = service.getAllProducts().size();
        assertEquals(expected, actual);
    }

    @Test
    public void checkoutTest() {
        int initialContentSize = service.getAllProducts().size();
        ensureShoppingBasketNotEmpty(initialContentSize);

        service.checkout();

        int expected = 0;
        int actual = service.getAllProducts().size();
        assertEquals(expected, actual);
    }
}
