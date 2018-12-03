package br.com.goschool.goschool_mobile.Modulos;

public class Estudante {

    private int id;
    private String nome;
    private String cep;
    private int cpf;
    private String matricula;
    private String nasc;
    private int rg;
    private String sexo;

    public String getEstudanteNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEstudanteNome(String nomeEstudante) {
        this.nome = nomeEstudante;
    }

    public String getEstudanteCEP() {
        return cep;
    }

    public void setEstudanteCEP(String cep) {
        this.cep = cep;
    }

    public int getEstudanteCPF() {
        return cpf;
    }

    public void setEstudanteCPF(int cpf) {
        this.cpf = cpf;
    }

    public String getEstudanteMatricula() {
        return matricula;
    }

    public void setEstudanteMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEstudanteNasc() {
        return nasc;
    }

    public void setEstudanteNasc(String nasc) {
        this.nasc = nasc;
    }

    public String getEstudanteSexo() {
        return sexo;
    }

    public void setEstudanteSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getEstudanteRg() {
        return rg;
    }

    public void setEstudanteRg(int rg) {
        this.rg = rg;
    }
    
}
