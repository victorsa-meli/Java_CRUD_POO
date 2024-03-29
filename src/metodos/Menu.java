package src.metodos;

import src.entity.Cliente;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Cliente> clientes = new ArrayList<Cliente>();


    public void telaPrincipal(){
        //BASE DE DADOS PARA TESTE
        clientes.add(new Cliente("João", 20, "12345678910", "01/01/2000"));
        clientes.add(new Cliente("Maria", 30, "12345678911", "01/01/1990"));
        clientes.add(new Cliente("José", 40, "12345678912", "01/01/1980"));
        clientes.add(new Cliente("Pedro", 50, "12345678913", "01/01/1970"));
        clientes.add(new Cliente("Ana", 60, "12345678914", "01/01/1960"));

        while (true) {
            menuPrincipal();

        }
    }

    private void menuPrincipal() {
        System.out.println("______________________________________");
        System.out.println("            MENU PRINCIPAL            ");
        System.out.println("                                      ");
        System.out.println("            1 - Cadastrar             ");
        System.out.println("            2 - Listar Todos Clientes ");
        System.out.println("            3 - Pesquisar             ");
        System.out.println("            4 - Editar                ");
        System.out.println("            5 - Excluir               ");
        System.out.println("            6 - Sair                  ");
        System.out.println("______________________________________");
        var option = scanner.next();

        switch (option) {
            case "1" -> cadastroMenu(clientes);
            case "2" -> listarClientes(clientes);
            case "3" -> pesquisarCliente(clientes);
            case "4" -> alterarMenu(clientes);
            case "5" -> excluirMenu(clientes);
            case "6" -> System.exit(0);
            default -> {
                System.out.println("Opção inválida!");
                menuPrincipal();
            }
        }
    }

    private void listarClientes(ArrayList<Cliente> clientes) {
        if (clientes.isEmpty()) {
            cleanConsole();
            System.out.println("Não há clientes cadastrados!");
            menuPrincipal();
        } else {
            for (Cliente cliente : clientes) {
                System.out.println("\n Nome: " + cliente.getNome()+
                                " ||  CPF: "+formatarCpf(cliente.getCpf())+
                        " ||  Idade: " + cliente.getIdade() +
                        " ||  Data de Nascimento: " +formatarDataNascimento(cliente.getDataNascimento()));

            }
        }

        System.out.println("Digite 1 - Voltar ao menu principal.");
        var op = scanner.nextInt();
        try {
            if (op == 1) {
                menuPrincipal();
            }
        } catch (Exception e) {
            cleanConsole();
            System.out.println("Opção inválida");
            listarClientes(clientes);
        }
    }

    private void excluirMenu(ArrayList<Cliente> clientes) {
        System.out.println("Digite o cpf do cliente que deseja excluir: ");
        String cpf = scanner.next().replaceAll("\\D", "");


        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados!");
            menuPrincipal();
        }

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCpf().equals(cpf)) {
                clientes.remove(i);
                System.out.println("Cliente excluído com sucesso!");
                msgRetornoExclusao(clientes);
            } else {
                System.out.println("CPF não encontrado! ");
                msgRetornoExclusao(clientes);
            }
        }
    }

    private void msgRetornoExclusao(ArrayList<Cliente> clientes) {
        System.out.println("Digite 1 - Excluir outro cliente.");
        System.out.println("Digite 2 - Voltar ao menu principal.");
        var op = scanner.nextInt();
        try {
            if (op == 1) {
                excluirMenu(clientes);
            } else if (op == 2) {
                menuPrincipal();

            }

        } catch (Exception e) {
            cleanConsole();
            System.out.println("Opção inválida");
            msgRetornoPesquisa(clientes);
        }
    }

    private void alterarMenu(ArrayList<Cliente> clientes) {
        System.out.println("Digite o cpf do cliente que deseja alterar: ");
        var cpf = scanner.next().replaceAll("\\D", "");


        if (clientes.isEmpty()) {
            cleanConsole();
            System.out.println("Não há clientes cadastrados!");
            menuPrincipal();
        }
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                System.out.println("NOME: ");
                String nome = scanner.skip("\\R?").nextLine().toUpperCase();
                System.out.println("IDADE: ");
                var idade = scanner.nextInt();
                System.out.println("DATA DE NASCIMENTO: ");
                var dataNascimento = scanner.next();

                cliente.setNome(nome);
                cliente.setIdade(idade);
                cliente.setDataNascimento(dataNascimento);

                System.out.println("Cliente alterado com sucesso!");
                msgRetornoAlteracao(clientes);
            } else {
                cleanConsole();
                System.out.println("CPF não encontrado! ");
                alterarMenu(clientes);
            }
        }


    }

    private void pesquisarCliente(ArrayList<Cliente> clientes)  {
        System.out.println("Qual o cpf do cliente que deseja consultar ? ");
        var cpf = scanner.next().replaceAll("\\D", "");

        if (clientes.isEmpty()) {
            cleanConsole();
            System.out.println("Não há clientes cadastrados!");
            menuPrincipal();
        }
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equalsIgnoreCase(cpf)) {
                System.out.println("NOME: " + cliente.getNome().toUpperCase());
                System.out.println("IDADE: " + cliente.getIdade());
                System.out.println("CPF: " + formatarCpf(cliente.getCpf()));
                System.out.println("DATA DE NASCIMENTO: "+formatarDataNascimento(cliente.getDataNascimento()));
                System.out.println("\n_____________________________________________________");
                msgRetornoPesquisa(clientes);
            } else {
                System.out.println("CPF não encontrado! ");
                msgRetornoPesquisa(clientes);
            }


        }


    }



    public String formatarCpf(String cpf) {

        // Verificar se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return cpf; // Retorna a string original se não tiver 11 dígitos
        }

        // Aplicar a máscara
        StringBuilder cpfFormatado = new StringBuilder();
        cpfFormatado.append(cpf.substring(0, 3));
        cpfFormatado.append(".");
        cpfFormatado.append(cpf.substring(3, 6));
        cpfFormatado.append(".");
        cpfFormatado.append(cpf.substring(6, 9));
        cpfFormatado.append("-");
        cpfFormatado.append(cpf.substring(9, 11));

        return cpfFormatado.toString();
    }

    public static String limparFormatacaoCPF(String cpfFormatado) {
        // Remover caracteres não numéricos
        return cpfFormatado.replaceAll("\\D", "");
    }

    private void msgRetornoAlteracao(ArrayList<Cliente> clientes) {
        System.out.println("Digite 1 - para alterar outro cliente.");
        System.out.println("Digite 2 - Voltar ao menu principal.");
        var op = scanner.nextInt();
        try {
            if (op == 1) {
                alterarMenu(clientes);
            } else if (op == 2) {
                menuPrincipal();

            }
        } catch (Exception e) {
            cleanConsole();
            System.out.println("Opção inválida");
            msgRetornoPesquisa(clientes);
        }
    }

    private void msgRetornoPesquisa(ArrayList<Cliente> clientes) {
        System.out.println("Digite 1 - Para uma nova consulta.");
        System.out.println("Digite 2 - Voltar ao menu principal.");
        var op = scanner.nextInt();
        try {
            if (op == 1) {
                pesquisarCliente(clientes);
            } else if (op == 2) {
                menuPrincipal();

            }

        } catch (Exception e) {
            cleanConsole();
            System.out.println("Opção inválida");
            msgRetornoPesquisa(clientes);
        }
    }


    private void msgRetornoCadastro(ArrayList<Cliente> clientes)  {
        System.out.println("Digite 1 - Para uma novo cadastro.");
        System.out.println("Digite 2 - Voltar ao menu principal.");
        var op = scanner.nextInt();
        try {
            if (op == 1) {
                cadastroMenu(clientes);
            } else if (op == 2) {
                menuPrincipal();
            }

        } catch (Exception e) {
            cleanConsole();
            System.out.println("Opção inválida");
            msgRetornoCadastro(clientes);
        }
    }

    private void cadastroMenu(ArrayList<Cliente> clientes)  {
        System.out.println("NOME: ");
        String nome = scanner.skip("\\R?").nextLine().toUpperCase();
        System.out.println("IDADE: ");
        var idade = scanner.nextInt();
        var cpf = cadastraCpf();
        System.out.println("DATA DE NASCIMENTO: ");
        var dataNascimento = scanner.next();

        clientes.add(new Cliente(nome, idade, cpf, dataNascimento));
        cleanConsole();
        System.out.println("Cliente cadastrado com sucesso!");
        msgRetornoCadastro(clientes);

    }

    private String cadastraCpf() {
        String cpf ="";
        System.out.println("Digite o CPF: ");
        cpf = scanner.next();

        if (!validaCpf(cpf)) {
            do {
                System.out.println("CPF inválido!");
                System.out.println("Digite um CPF válido: ");
                cpf = scanner.next();

            }while (!validaCpf(cpf));

        }


        //verifica se o cpf já está cadastrado
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                cleanConsole();
                System.out.println("CPF já cadastrado");
                cadastraCpf();
            }
        }

        return cpf;
    }

    private boolean validaCpf(String cpf) {
            // Remover caracteres não numéricos
            cpf = cpf.replaceAll("\\D", "");

            // Verificar se o CPF tem 11 dígitos
            if (cpf.length() != 11) {
                return false;
            }
            // Verificar se todos os dígitos são iguais (caso comum de CPF inválido)
            boolean todosDigitosIguais = cpf.matches("(\\d)\\1{10}");
            if (todosDigitosIguais) {
                return false;
            }

            // Validar dígitos verificadores
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * (10 - i);
            }

            int digito1 = 11 - (soma % 11);
            if (digito1 > 9) {
                digito1 = 0;
            }

            if ((cpf.charAt(9) - '0') != digito1) {
                return false;
            }

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * (11 - i);
            }

            int digito2 = 11 - (soma % 11);
            if (digito2 > 9) {
                digito2 = 0;
            }

            return (cpf.charAt(10) - '0') == digito2;
        }


    public static String formatarDataNascimento(String dataNascimento) {
        // Remover caracteres não numéricos
        dataNascimento = dataNascimento.replaceAll("\\D", "");

        // Verificar se a data de nascimento tem 8 dígitos
        if (dataNascimento.length() != 8) {
            return dataNascimento; // Retorna a string original se não tiver 8 dígitos
        }

        // Aplicar a máscara
        StringBuilder dataFormatada = new StringBuilder();
        dataFormatada.append(dataNascimento.substring(0, 2));
        dataFormatada.append("/");
        dataFormatada.append(dataNascimento.substring(2, 4));
        dataFormatada.append("/");
        dataFormatada.append(dataNascimento.substring(4, 8));

        return dataFormatada.toString();
    }



    private void cleanConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }

    }



}
