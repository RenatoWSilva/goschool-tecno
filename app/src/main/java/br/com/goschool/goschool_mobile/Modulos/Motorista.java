package br.com.goschool.goschool_mobile.Modulos;

public class Motorista {

    private int id;
    private String nome;
    private String cnh;
    private int cpf;
    private String sexo;
    private String nasc;
    private int rg;

    public String getMotororistaNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMotororistaNome(String nomeMotorista) {
        this.nome = nomeMotorista;
    }

    public String getMotororistaCNH() {
        return cnh;
    }

    public void setMotororistaCNH(String cnh) {
        this.cnh = cnh;
    }

    public int getMotororistaCPF() {
        return cpf;
    }

    public void setMotororistaCPF(int cpf) {
        this.cpf = cpf;
    }

    public String getMotoristaNasc() {
        return nasc;
    }

    public void setMotoristaNasc(String nasc) {
        this.nasc = nasc;
    }

    public String getMotoristaSexo() {
        return sexo;
    }

    public void setMotoristaSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getMotoristaRg() {
        return rg;
    }

    public void setMotoristaRg(int rg) {
        this.rg = rg;
    }


}
