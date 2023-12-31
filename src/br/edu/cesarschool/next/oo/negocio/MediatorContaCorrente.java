package br.edu.cesarschool.next.oo.negocio;

import br.edu.cesarschool.next.oo.dao.DAOContaCorrente;
import br.edu.cesarschool.next.oo.entidade.ContaCorrente;
import br.edu.cesarschool.next.oo.entidade.ContaPoupanca;
import br.edu.cesarschool.next.oo.entidade.RegistroIdentificavel;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MediatorContaCorrente {

    DAOContaCorrente daoContaCorrente = new DAOContaCorrente();

    public MediatorContaCorrente() {

    }

    public String incluir(ContaCorrente conta) {

        if (stringNulaOuVazia(conta.getNumero())) {
            return "Número da conta inválido ou vazio.";
        } else if (conta.getNumero().length() < 5 || conta.getNumero().length() > 8) {
            return "O número da conta deve ter entre 5 e 8 caracteres.";
        } else if (conta.getSaldo() < 0) {
            return "O saldo não pode ser menor que zero.";
        } else if (stringNulaOuVazia(conta.getNomeCorrentista())) {
            return "Nome do correntista inválido ou vazio.";
        } else if (conta.getNomeCorrentista().length() > 60) {
            return "O nome do correntista deve conter no máximo 60 caracteres.";
        } else if (conta instanceof ContaPoupanca) {
            if (((ContaPoupanca) conta).getPercentualBonus() < 0) {
                return "O percentual de bônus não pode ser menor que zero.";
            } else {
                conta.setDataHoraCriacao(LocalDateTime.now());
                boolean ret = daoContaCorrente.incluir(conta);
                if (!ret) {
                    return "Conta já existente.";
                }
            }
        } else {
            conta.setDataHoraCriacao(LocalDateTime.now());
            boolean ret = daoContaCorrente.incluir(conta);
            if (!ret) {
                return "Conta já existente.";
            }
        }
        return null;
    }

    public String creditar(double valor, String numero) {

        if (valor < 0) {
            return "Valor não pode ser menor que zero.";
        } else if (stringNulaOuVazia(numero)) {
            return "Número da conta inválido ou vazio.";
        } else {
            ContaCorrente conta = daoContaCorrente.buscar(numero);
            if (conta == null) {
                return "Conta inexistente.";
            } else {
                conta.creditar(valor);
                daoContaCorrente.alterar(conta);
                return null;
            }
        }
    }

    public String debitar(double valor, String numero) {

        if (valor < 0) {
            return "Valor não pode ser menor que zero.";
        } else if (stringNulaOuVazia(numero)) {
            return "Número da conta inválido ou vazio.";
        } else {
            ContaCorrente conta = daoContaCorrente.buscar(numero);
            if (conta == null) {
                return "Conta inexistente.";
            } else {
                double valorComCPMF = valor + (valor * conta.obterAliquotaCPMF());
                if (conta.getSaldo() < valorComCPMF) {
                    return "O valor solicitado excede o saldo da conta.";
                } else {
                    conta.debitar(valor);
                    daoContaCorrente.alterar(conta);
                    return null;
                }
            }
        }
    }

    public ContaCorrente buscar(String numero) {

        if (stringNulaOuVazia(numero)) {
            return null;
        } else {
            return daoContaCorrente.buscar(numero);
        }
    }

    public List<ContaCorrente> gerarRelatorioGeral() {
        ContaCorrente[] contas = daoContaCorrente.buscarTodos();
        List<ContaCorrente> listaContas = Arrays.asList(contas);
        listaContas.sort(new ComparadorContaCorrenteSaldo());
        return listaContas;
    }

    public String excluir(String numero) {

        ContaCorrente conta = daoContaCorrente.buscar(numero);
        if (conta == null) {
            return "Conta inexistente.";
        } else {
            boolean excluir = daoContaCorrente.excluir(numero);
            return null;
        }
    }

    private boolean stringNulaOuVazia(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

}
