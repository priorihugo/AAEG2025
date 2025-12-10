package org.example.padroesestruturais.proxy;

import org.example.padroescriacao.factorymethod.IServico;
import org.example.padroesestruturais.proxy.enums.NivelAcesso;

/**
 * Protection Proxy - Adiciona controle de acesso a serviços
 *
 * PADRÃO PROXY: Proxy (Protection Proxy)
 *
 * Intercepta chamadas e valida permissões antes de delegar ao RealSubject.
 * Implementa segurança baseada em níveis hierárquicos de acesso.
 *
 * Responsabilidades:
 * - Validar nível de acesso do usuário antes de executar operações
 * - Permitir operações de leitura sem autenticação (getValorServico)
 * - Lançar SecurityException com mensagem descritiva em caso de negação
 * - Delegar para serviço real apenas quando autorizado
 *
 * Níveis de permissão:
 * - executar(): Requer MECANICO ou superior
 * - cancelar(): Requer GERENTE ou superior
 * - getValorServico(): Público (sem restrição)
 *
 * Benefício: Segurança centralizada sem poluir lógica de negócio
 */
public class ServicoProtegidoProxy implements IServico {

    private final IServico servicoReal;
    private final String nomeUsuario;
    private final NivelAcesso nivelAcessoUsuario;
    private final NivelAcesso nivelMinimoExecucao;
    private final NivelAcesso nivelMinimoCancelamento;

    public ServicoProtegidoProxy(
            IServico servicoReal,
            String nomeUsuario,
            NivelAcesso nivelAcessoUsuario
    ) {
        this(servicoReal, nomeUsuario, nivelAcessoUsuario, NivelAcesso.MECANICO, NivelAcesso.GERENTE);
    }

    public ServicoProtegidoProxy(
            IServico servicoReal,
            String nomeUsuario,
            NivelAcesso nivelAcessoUsuario,
            NivelAcesso nivelMinimoExecucao,
            NivelAcesso nivelMinimoCancelamento
    ) {
        if (servicoReal == null) {
            throw new IllegalArgumentException("Serviço real não pode ser nulo");
        }
        if (nomeUsuario == null || nomeUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome de usuário não pode ser vazio");
        }
        if (nivelAcessoUsuario == null) {
            throw new IllegalArgumentException("Nível de acesso não pode ser nulo");
        }

        this.servicoReal = servicoReal;
        this.nomeUsuario = nomeUsuario;
        this.nivelAcessoUsuario = nivelAcessoUsuario;
        this.nivelMinimoExecucao = nivelMinimoExecucao;
        this.nivelMinimoCancelamento = nivelMinimoCancelamento;
    }

    @Override
    public String executar() {
        if (!verificarPermissao(nivelMinimoExecucao)) {
            throw new SecurityException(
                    String.format(
                            "Acesso negado: Usuário '%s' (nível: %s) não tem permissão para executar serviço. Nível mínimo requerido: %s",
                            nomeUsuario,
                            nivelAcessoUsuario.getDescricao(),
                            nivelMinimoExecucao.getDescricao()
                    )
            );
        }

        return servicoReal.executar();
    }

    @Override
    public String cancelar() {
        if (!verificarPermissao(nivelMinimoCancelamento)) {
            throw new SecurityException(
                    String.format(
                            "Acesso negado: Usuário '%s' (nível: %s) não tem permissão para cancelar serviço. Nível mínimo requerido: %s",
                            nomeUsuario,
                            nivelAcessoUsuario.getDescricao(),
                            nivelMinimoCancelamento.getDescricao()
                    )
            );
        }

        return servicoReal.cancelar();
    }

    @Override
    public Double getValorServico() {
        // Operação de leitura pública - não requer autenticação
        return servicoReal.getValorServico();
    }

    private boolean verificarPermissao(NivelAcesso nivelMinimo) {
        return nivelAcessoUsuario.temPermissao(nivelMinimo);
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public NivelAcesso getNivelAcessoUsuario() {
        return nivelAcessoUsuario;
    }

    public IServico getServicoReal() {
        return servicoReal;
    }
}
