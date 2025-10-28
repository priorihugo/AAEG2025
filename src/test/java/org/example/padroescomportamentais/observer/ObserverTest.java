package org.example.padroescomportamentais.observer;

import org.example.padroescomportamentais.observer.observers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ObserverTest {

    private AtendimentoSubject subject;
    private TestObserver observer1;
    private TestObserver observer2;

    @BeforeEach
    void setUp() {
        subject = new AtendimentoSubject();
        observer1 = new TestObserver();
        observer2 = new TestObserver();
    }

    @Test
    void deveNotificarObserversQuandoEstadoMudar() {
        subject.attach(observer1);
        subject.attach(observer2);

        subject.setEstado("AGENDADO");

        assertEquals(1, observer1.getUpdateCount());
        assertEquals(1, observer2.getUpdateCount());
    }

    @Test
    void deveAdicionarObserver() {
        subject.attach(observer1);
        subject.setEstado("AGENDADO");

        assertEquals(1, observer1.getUpdateCount());
    }

    @Test
    void deveRemoverObserver() {
        subject.attach(observer1);
        subject.detach(observer1);
        subject.setEstado("AGENDADO");

        assertEquals(0, observer1.getUpdateCount());
    }

    @Test
    void deveNotificarApenasObserversAtivos() {
        subject.attach(observer1);
        subject.attach(observer2);
        subject.detach(observer1);

        subject.setEstado("AGENDADO");

        assertEquals(0, observer1.getUpdateCount());
        assertEquals(1, observer2.getUpdateCount());
    }

    @Test
    void devePermitirMultiplasNotificacoes() {
        subject.attach(observer1);

        subject.setEstado("AGENDADO");
        subject.setEstado("EM ANDAMENTO");
        subject.setEstado("CONCLUÍDO");

        assertEquals(3, observer1.getUpdateCount());
    }

    @Test
    void deveManterEstadoAtualizado() {
        subject.setEstado("AGENDADO");
        assertEquals("AGENDADO", subject.getEstado());

        subject.setEstado("EM ANDAMENTO");
        assertEquals("EM ANDAMENTO", subject.getEstado());
    }

    @Test
    void deveGerarDescricaoCorreta() {
        subject.setEstado("CONCLUÍDO");
        assertEquals("Estado alterado para: CONCLUÍDO", subject.getDescricao());
    }

    @Test
    void devePermitirAdicionarMesmosObserversMultiplasVezes() {
        subject.attach(observer1);
        subject.attach(observer1);

        subject.setEstado("AGENDADO");

        assertEquals(2, observer1.getUpdateCount());
    }

    private static class TestObserver implements Observer {
        private int updateCount = 0;

        @Override
        public void update(Subject subject) {
            updateCount++;
        }

        public int getUpdateCount() {
            return updateCount;
        }
    }
}
