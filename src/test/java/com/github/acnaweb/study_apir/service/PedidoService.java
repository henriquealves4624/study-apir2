package com.github.acnaweb.study_apir.service;

import com.github.acnaweb.study_apir.dto.item.ItemRequestCreate;
import com.github.acnaweb.study_apir.dto.pedido.PedidoRequestCreate;
import com.github.acnaweb.study_apir.model.Item;
import com.github.acnaweb.study_apir.model.Pedido;
import com.github.acnaweb.study_apir.model.PedidoStatus;
import com.github.acnaweb.study_apir.model.Produto;
import com.github.acnaweb.study_apir.repository.PedidoRepository;
import com.github.acnaweb.study_apir.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenPedidoRequestWhenCreateThenPedidoIsSaved() {
        // Given
        PedidoRequestCreate pedidoRequestCreate = new PedidoRequestCreate();
        pedidoRequestCreate.setDataPedido(LocalDate.now());
        pedidoRequestCreate.setDataEntrega(LocalDate.now());

        Pedido pedidoMock = new Pedido();
        pedidoMock.setId(1L);

        // Mocking Produto
        Produto produtoMock = new Produto();
        produtoMock.setId(1L);
        produtoMock.setNome("Produto A");

        // Mocking Item
        List<ItemRequestCreate> items = new ArrayList<>();
        ItemRequestCreate item = new ItemRequestCreate();
        item.setProduto_id(1L);
        item.setQuantidade(new BigDecimal(2));
        item.setValor(new BigDecimal(10));
        items.add(item);

        pedidoRequestCreate.setItems(items);

        // When
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoMock));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoMock);

        Pedido resultado = pedidoService.create(pedidoRequestCreate);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

}

