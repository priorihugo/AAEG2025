package org.example.padroesestruturais.bridge.pagamento;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PagamentoTest {

    @Test
    void testPagamentoAVistaComDinheiro() {
        IMetodoPagamento dinheiro = new PagamentoDinheiro();
        Pagamento pagamento = new PagamentoAVista(dinheiro, 1000.0, "Serviço de revisão");

        assertTrue(pagamento.efetuarPagamento());
        assertEquals("Dinheiro", pagamento.getMetodoPagamento().getNome());
        assertEquals(1000.0, pagamento.getValor());
    }

    @Test
    void testPagamentoAVistaComPIX() {
        IMetodoPagamento pix = new PagamentoPIX();
        Pagamento pagamento = new PagamentoAVista(pix, 500.0, "Troca de óleo");

        assertTrue(pagamento.efetuarPagamento());
        assertEquals("PIX", pagamento.getMetodoPagamento().getNome());
        assertEquals(0.0, pix.getTaxa());
    }

    @Test
    void testPagamentoParceladoComCartao() {
        IMetodoPagamento cartao = new PagamentoCartao();
        PagamentoParcelado pagamento = new PagamentoParcelado(cartao, 3000.0, "Manutenção completa", 6);

        assertTrue(pagamento.efetuarPagamento());
        assertEquals(6, pagamento.getNumeroParcelas());
        assertEquals("Cartão", pagamento.getMetodoPagamento().getNome());
    }

    @Test
    void testPagamentoParceladoComBoleto() {
        IMetodoPagamento boleto = new PagamentoBoleto();
        PagamentoParcelado pagamento = new PagamentoParcelado(boleto, 2000.0, "Conserto de motor", 4);

        assertTrue(pagamento.efetuarPagamento());
        assertEquals(4, pagamento.getNumeroParcelas());
        assertEquals("Boleto", pagamento.getMetodoPagamento().getNome());
    }

    @Test
    void testDescontoAVistaComDinheiro() {
        IMetodoPagamento dinheiro = new PagamentoDinheiro();
        PagamentoAVista pagamento = new PagamentoAVista(dinheiro, 1000.0, "Serviço teste");

        assertEquals(0.05, pagamento.getDesconto()); // 5% de desconto
    }

    @Test
    void testDescontoAVistaComPIX() {
        IMetodoPagamento pix = new PagamentoPIX();
        PagamentoAVista pagamento = new PagamentoAVista(pix, 1000.0, "Serviço teste");

        assertEquals(0.05, pagamento.getDesconto()); // 5% de desconto
    }

    @Test
    void testSemDescontoComCartao() {
        IMetodoPagamento cartao = new PagamentoCartao();
        PagamentoAVista pagamento = new PagamentoAVista(cartao, 1000.0, "Serviço teste");

        assertEquals(0.0, pagamento.getDesconto()); // Sem desconto
    }

    @Test
    void testTaxaCartao() {
        IMetodoPagamento cartao = new PagamentoCartao();
        assertEquals(0.029, cartao.getTaxa()); // 2.9%
    }

    @Test
    void testTaxaBoleto() {
        IMetodoPagamento boleto = new PagamentoBoleto();
        assertEquals(0.015, boleto.getTaxa()); // 1.5%
    }

    @Test
    void testValidacaoParcelasParaDinheiro() {
        IMetodoPagamento dinheiro = new PagamentoDinheiro();
        PagamentoParcelado pagamento = new PagamentoParcelado(dinheiro, 1000.0, "Serviço", 5);

        // Deve ser ajustado para 1x automaticamente
        assertEquals(1, pagamento.getNumeroParcelas());
    }

    @Test
    void testValidacaoParcelasParaPIX() {
        IMetodoPagamento pix = new PagamentoPIX();
        PagamentoParcelado pagamento = new PagamentoParcelado(pix, 1000.0, "Serviço", 3);

        // Deve ser ajustado para 1x automaticamente
        assertEquals(1, pagamento.getNumeroParcelas());
    }
}
