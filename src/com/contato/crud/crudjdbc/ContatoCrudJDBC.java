package com.contato.crud.crudjdbc;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ContatoCrudJDBC {

    public void salvarContato(Contato contato) {
        Connection conn = this.gerarConexao();
        PreparedStatement insereSt = null;
        String sql = "insert into contato (nome, email, dataCadastro) values (?, ?, ?)";
        try {
            insereSt = conn.prepareStatement(sql);
            insereSt.setString(1, contato.getNome());
            insereSt.setString(2, contato.getEmail());
            insereSt.setDate(3, contato.getDataCadastro());
            insereSt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao incluir contato. Mensagem: " + e.getMessage());
        } finally {
            try {
                insereSt.close();
                conn.close();
            } catch (Throwable e) {
                System.out.println("Erro ao fechar operações de inserção. Mensage: " + e.getMessage());
            }
        }
    }

    public void atualizarContato(Contato contato) {
        Connection conn = this.gerarConexao();
        PreparedStatement atualizaSt = null;
        String sql = "update contato set nome=?, email=?, dataCadastro=? where codigo=?";
        try {
            atualizaSt = conn.prepareStatement(sql);
            atualizaSt.setString(1, contato.getNome());
            atualizaSt.setString(2, contato.getEmail());
            atualizaSt.setDate(3, contato.getDataCadastro());
            atualizaSt.setInt(4, contato.getCodigo());
            atualizaSt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar contato. Mensagem: " + e.getMessage());
        } finally {
            try {
                atualizaSt.close();
                conn.close();
            } catch (Throwable e) {
                System.out.println("Erro ao fechar operações de atualização. Mensagem: " + e.getMessage());
            }
        }
    }

    public void excluirContato(Contato contato) {
        Connection conn = this.gerarConexao();
        PreparedStatement excluiSt = null;

        String sql = "delete from contato where codigo = ?";

        try {
            excluiSt = conn.prepareStatement(sql);
            excluiSt.setInt(1, contato.getCodigo());
            excluiSt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir contato. Mensagem: " + e.getMessage());
        } finally {
            try {
                excluiSt.close();
                conn.close();
            } catch (Throwable e) {
                System.out.println("Erro ao fechar operações de exclusão. Mensagem: " + e.getMessage());
            }
        }
    }

    public List<Contato> listarContato() {
        Connection conn = this.gerarConexao();
        List<Contato> contatos = new ArrayList<Contato>();
        Statement consulta = null;
        ResultSet resultado = null;
        Contato contato = null;
        String sql = "select * from contato";
        try {
            consulta = conn.createStatement();
            resultado = consulta.executeQuery(sql);
            while (resultado.next()) {
                contato = new Contato();
                contato.setCodigo(resultado.getInt("codigo"));
                contato.setNome(resultado.getString("nome"));
                contato.setEmail(resultado.getString("email"));
                contato.setDataCadastro(resultado.getDate("dataCadastro"));
                contatos.add(contato);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar código do contato. Mensagem: " + e.getMessage());
        } finally {
            try {
                consulta.close();
                resultado.close();
                conn.close();
            } catch (Throwable e) {
                System.out.println("Erro ao fechar operações de consulta. Mensagem: " + e.getMessage());
            }
        }
        return contatos;
    }

    public Contato buscarContato(int valor) {
        Connection conn = this.gerarConexao();
        PreparedStatement consulta = null;
        ResultSet resultado = null;
        Contato contato = null;
        String sql = "select * from contato where codigo = ?";

        try {
            consulta = conn.prepareStatement(sql);
            consulta.setInt(1, valor);
            resultado = consulta.executeQuery();

            if (resultado.next()) {
                contato = new Contato();
                contato.setCodigo(resultado.getInt("codigo"));
                contato.setNome(resultado.getString("nome"));
                contato.setEmail(resultado.getString("email"));
                contato.setDataCadastro(resultado.getDate("dataCadastro"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar código do contato. Mensagem: " + e.getMessage());
        } finally {
            try {
                consulta.close();
                resultado.close();
                conn.close();
            } catch (Throwable e) {
                System.out.println("Erro ao fechar operações de consulta. Mensagem: " + e.getMessage());
            }
        }
        return contato;
    }

    public Connection gerarConexao() {
        Connection conn = null;

        try {
            // Registrando a classe JDBC no sistema em tempo de execução
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/agenda";
            String usuario = "root";
            String senha = "";
            conn = DriverManager.getConnection(url, usuario, senha);
        } catch (ClassNotFoundException e) {
            System.out.println("Classe não encontrada. Erro: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro de SQL. Erro: " + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        ContatoCrudJDBC contatoCRUDJDBC = new ContatoCrudJDBC();

        Contato amaya = new Contato();
        amaya.setNome("Amaya S");
        amaya.setEmail("amaya@gmail.com.br");
        amaya.setDataCadastro(new Date(System.currentTimeMillis()));
        contatoCRUDJDBC.salvarContato(amaya);

        Contato john = new Contato();
        john.setNome("John Doe");
        john.setEmail("johndoe@gmail.com.br");
        john.setDataCadastro(new Date(System.currentTimeMillis()));
        contatoCRUDJDBC.salvarContato(john);

        Contato jane = new Contato();
        jane.setNome("Jane Doe");
        jane.setEmail("janedoe@gmail.com.br");
        jane.setDataCadastro(new Date(System.currentTimeMillis()));
        contatoCRUDJDBC.salvarContato(jane);

        System.out.println("Contatos cadastrados: " + contatoCRUDJDBC.listarContato().size());
    }
}
