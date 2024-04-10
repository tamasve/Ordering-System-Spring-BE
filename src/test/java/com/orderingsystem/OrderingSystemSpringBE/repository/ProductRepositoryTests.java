package com.orderingsystem.OrderingSystemSpringBE.repository;

import com.orderingsystem.OrderingSystemSpringBE.entity.Category;
import com.orderingsystem.OrderingSystemSpringBE.entity.Product;
import com.orderingsystem.OrderingSystemSpringBE.entity.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

// -- ASSERTJ --

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductRepositoryTests {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void ProductRepository_Save_ReturnSavedProduct() {

        // Arrange + Act
        Category category = categoryRepository.save( new Category("computer") );
        Product product = productRepository.save ( new Product("mouse", "pcs", 3, category) );

        // Assert
        assertThat( product ).isNotNull();
        assertThat( product.getId() ).isGreaterThan(0);
    }

    @Test
    public void ProductRepository_findAllWithCatName_ReturnFoundProducts() {

        // Arrange + Act
        Category category1 = categoryRepository.save( new Category("computer") );
        Product product1 = productRepository.save ( new Product("mouse", "pcs", 3, category1) );
        Category category2 = categoryRepository.save( new Category("food") );
        Product product2 = productRepository.save ( new Product("milk", "litre", 1, category2) );

        // Assert
        List<ProductDTO> productList = productRepository.findAllWithCatName();

        assertThat( productList ).isNotNull();
        assertThat( productList.size() ).isEqualTo(2);

        assertThat( productList.get(0).getName() ).isEqualTo("mouse");
        assertThat( productList.get(0).getCategory() ).isEqualTo("computer");

        assertThat( productList.get(1).getUnit() ).isEqualTo("litre");
        assertThat( productList.get(1).getCategory() ).isEqualTo("food");

    }
}
