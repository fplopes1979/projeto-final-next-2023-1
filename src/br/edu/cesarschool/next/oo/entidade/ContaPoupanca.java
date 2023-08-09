package br.edu.cesarschool.next.oo.entidade;

import java.io.Serializable;

public class ContaPoupanca extends ContaCorrente {

    private double percentualBonus;

    public ContaPoupanca(double percentualBonus) {
        this.percentualBonus = percentualBonus;
    }

    public ContaPoupanca(String numero, double saldo, String nomeCorrentista, double percentualBonus) {
        super(numero, saldo, nomeCorrentista);
        this.percentualBonus = percentualBonus;
    }

    public double getPercentualBonus() {
        return percentualBonus;
    }

    public void setPercentualBonus(double percentualBonus) {
        this.percentualBonus = percentualBonus;
    }

    @Override
    public void creditar(double valor) {

         super.creditar(super.getSaldo() + valor * (1 + percentualBonus/100));
    }

    @Override
    public String toString() {

        return super.toString() + "\nPercentual de Bonus: " + percentualBonus;
    }

    @Override
    public double obterAliquotaCPMF() {
        return 0;
    }

}
