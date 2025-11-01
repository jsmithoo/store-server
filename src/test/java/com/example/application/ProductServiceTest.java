package com.example.application;
import com.example.DataProviderProducts;
import com.example.adapters.outbound.repository.ProductJpaRepository;
import com.example.adapters.outbound.repository.entities.Product;
import com.example.domain.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Disabled
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductJpaRepository mockProductJpaRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(mockProductJpaRepository);
    }


    @Test
    public void testFindById() {
        //Given => Teniendo
        Long id = 1L;
        //When => Cuando
        Product expectedProduct = DataProviderProducts.productMock();
        when(mockProductJpaRepository.findById(id)).thenReturn(Optional.of(expectedProduct));
        Product product = this.productService.findById(id);

        //Then => Entonces
        assertThat(product).isEqualTo(expectedProduct);
    }

    @Test
    public void testFindById_ProductDoesNotExist() {
        // Given
        Long id = 1L;
        when(mockProductJpaRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrowsExactly(NotFoundException.class, () -> {
            productService.findById(id);
        });

        assertEquals("La entidad Product con id 1 no existe", exception.getMessage());
    }



    @Test
    public void testFindAllProducts_WithKeyword() {
        // Given
        String keyword = "Coche";
        int offset = 0;
        int limit = 10;

        List<Product> expectedProducts = DataProviderProducts.productListMock();

        when(mockProductJpaRepository.findByNameProductContainingOrBrandProductContainingOrDescriptionProduct(
                keyword, keyword, keyword)).thenReturn(expectedProducts);

        // When
        List<Product> result = productService.findAllProducts(keyword, offset, limit);

        // Then
        assertThat(result)
                .hasSize(expectedProducts.size())
                .isEqualTo(expectedProducts);
        verify(mockProductJpaRepository, never()).findAll(any(PageRequest.class));  // Verifica que no se llama a findAll
    }

    @Test
    public void testFindAllProducts_WithoutKeyword() {
        // Given
        String keyword = "";  // O puede ser `null`
        int offset = 0;
        int limit = 2;

        List<Product> expectedProducts = DataProviderProducts.productListMock();
        Page<Product> page = new PageImpl<>(expectedProducts);

        when(mockProductJpaRepository.findAll(PageRequest.of(offset, limit))).thenReturn(page);

        // When
        List<Product> result = productService.findAllProducts(keyword, offset, limit);

        // Then
        assertEquals(expectedProducts.size(), result.size());
        assertEquals(expectedProducts, result);
        verify(mockProductJpaRepository, never()).findByNameProductContainingOrBrandProductContainingOrDescriptionProduct(anyString(), anyString(), anyString());
    }

    @Test
    public void TestFindByNameProduct() {
        // Given
        String nameProduct = "Coche";
        Product expectedProduct = DataProviderProducts.productMock();
        when(this.mockProductJpaRepository.findByNameProduct(nameProduct)).thenReturn(Optional.of(expectedProduct));

        // When
        Product product = this.productService.findByNameProduct(nameProduct);

        // Then

        assertEquals(nameProduct, product.getNameProduct());

    }

    @Test
    public void testFindByNameProduct_NotExist() {
        //Given
        String nameProduct = "Coche";
        when(this.mockProductJpaRepository.findByNameProduct(nameProduct)).thenReturn(Optional.empty());

        //Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productService.findByNameProduct(nameProduct);
        });

        verify(mockProductJpaRepository, times(1)).findByNameProduct(nameProduct);
    }

    @Test
    public void testSaveProduct() {
        //Given => Teniendo
        Product product = DataProviderProducts.productSaveMock();
        // when(productJpaRepository.save(product)).thenReturn(DataProviderProducts.productSaveMock());
        Product prod = this.productService.saveProduct(product);
        //Then => Entonces
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(this.mockProductJpaRepository).save(any(Product.class));
        verify(this.mockProductJpaRepository).save(productArgumentCaptor.capture());
        assertNotNull(productArgumentCaptor);
        assertEquals(6L, productArgumentCaptor.getValue().getId());
        assertEquals("Bicicleta", productArgumentCaptor.getValue().getNameProduct());
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;
        Product expectedProduct = DataProviderProducts.productMock();

        // When => Cuando
        when(mockProductJpaRepository.findById(id)).thenReturn(Optional.of(expectedProduct));
        this.productService.deleteProduct(id);

        // Then => Entonces
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.mockProductJpaRepository, times(1)).deleteById(longArgumentCaptor.capture());

        // Verifica que el ID capturado es el que esperamos
        assertEquals(1L, longArgumentCaptor.getValue());

    }


    @Test
    public void testDeleteById_ProductDoesNotExist() {
        // Given
        Long id = 1L;
        when(mockProductJpaRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productService.deleteProduct(id);
        });

        assertEquals("La entidad Product con id 1 no existe", exception.getMessage());
        verify(mockProductJpaRepository, never()).deleteById(id);
    }

    /*@Test
    public void testUpdateProduct() {
        // Given
        Long id = 1L;
        ProductUpdateParams productUpdateParams = DataProviderProducts.productUpdateParamsMock();

        //When
        when(this.productJpaRepository.findById(id)).thenReturn(DataProviderProducts.productMock());
        Product result = this.productService.updateProduct(id, productUpdateParams);

        //Then
        assertEquals(productUpdateParams, result);
    }*/


}
