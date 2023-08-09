package br.edu.cesarschool.next.oo.entidade;

public abstract class Conta extends RegistroIdentificavel {

    public abstract double obterAliquotaCPMF();
    public Conta() {
    }
    public abstract void debitar(double valor);

}
