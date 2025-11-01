package com.example.application;
import com.example.DataProviderProducts;
import com.example.DataProvidersOrder;
import com.example.adapters.outbound.repository.OrderJpaRepository;
import com.example.adapters.outbound.repository.entities.Order;
import com.example.adapters.outbound.repository.entities.Product;
import com.example.domain.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@Disabled
public class OrderServiceTest {
    @Mock
    private OrderJpaRepository orderJpaRepository;
    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void TestfindById(){
        //Given
        Long id = 1L;
        Order orderMock = DataProvidersOrder.orderListMock().get(0);

        //When
        when(orderJpaRepository.findById(anyLong())).thenReturn(Optional.of(orderMock));
        Order orderResult = this.orderService.findById(id);

        //Then
        assertEquals(orderMock.getId(), orderResult.getId());

    }

    @Test
    public void testFindById_ProductDoesNotExist() {
        // Given
        Long id = 1L;
        when(orderJpaRepository.findById(id)).thenReturn(Optional.empty());

        //Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            this.orderService.findById(id);
        } );

        assertEquals("La entidad Order con id 1 no existe", exception.getMessage());
        verify(orderJpaRepository, times(1)).findById(id);

    }

    @Test
    public void testSaveOrder(){
        //Given => Teniendo
        Order order = DataProvidersOrder.orderSaveMock();

        //When
        Order orderSave = orderService.saveOrder(order);

        //Then
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(this.orderJpaRepository).save(any(Order.class));
        verify(this.orderJpaRepository).save(orderArgumentCaptor.capture());
        assertNotNull(orderArgumentCaptor);
        assertEquals(4L, orderArgumentCaptor.getValue().getId());
        assertEquals("PC", orderArgumentCaptor.getValue().getName());

    }

    @Test
    public void testDeleteByIdOrder(){
        //Given
        Long id = 5L;

        // When => Cuando
        when(orderJpaRepository.findById(id)).thenReturn(DataProvidersOrder.orderMock());
        this.orderService.deleteByIdOrder(id);

        // Then => Entonces
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.orderJpaRepository, times(1)).deleteById(longArgumentCaptor.capture());

        // Verifica que el ID capturado es el que esperamos
        assertEquals(5L, longArgumentCaptor.getValue());
    }


    @Test
    public void testDeleteById_ProductDoesNotExist() {
        // Given
        Long id = 1L;
        when(orderJpaRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            orderService.deleteByIdOrder(id);
        });

        assertEquals("La entidad Order con id 1 no existe", exception.getMessage());
        verify(orderJpaRepository, never()).deleteById(id);
    }
}
