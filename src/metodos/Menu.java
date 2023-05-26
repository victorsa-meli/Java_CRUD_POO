package src.metodos;

import src.entity.Cliente;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Cliente> clientes = new ArrayList<Cliente>();


    public void telaPrincipal(){
        while (true) {
            menuPrincipal();
                var option = scanner.nextLine();

                switch (option) {
                    case "1" -> cadastroMenu(clientes);
                    case "2" -> listarClientes(clientes);
                    case "3" -> pesquisarCliente(clientes);
                    case "4" -> alterarMenu(clientes);
                    case "5" -> excluirMenu(clientes);
                    case "6" -> System.exit(0);
                    default -> {
                        cleanConsole();
                        System.out.println("Opção inválida!");
                        telaPrincipal();
                    }
                }

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
    }

    private void listarClientes(ArrayList<Cliente> clientes) {
        if (clientes.isEmpty()) {
            cleanConsole();
            System.out.println("Não há clientes cadastrados!");
            telaPrincipal();
        } else {
            for (Cliente cliente : clientes) {
                System.out.println("\n Nome: " + cliente.getNome()
                        + " ||  CPF: " + formatarCpf(cliente.getCpf())+
                        " ||  Idade: " + cliente.getIdade() + " ||  Data de Nascimento: " +
                        formatDataNascimento(cliente.getDataNascimento()));

            }
        }

        System.out.println("Digite 1 - Voltar ao menu principal.");
        var op = scanner.nextInt();
        try {
            if (op == 1) {
                telaPrincipal();
            }
        } catch (Exception e) {
            cleanConsole();
            System.out.println("Opção inválida");
            listarClientes(clientes);
        }
    }

    private void excluirMenu(ArrayList<Cliente> clientes) {
        System.out.println("Digite o cpf do cliente que deseja excluir: ");
        var cpf = scanner.next();

        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados!");
            telaPrincipal();
        }

        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                clientes.remove(cliente);
                System.out.println("Cliente excluído com sucesso!");
                msgRetornoExclusao(clientes);
            } else {
                cleanConsole();
                System.out.println("CPF não encontrado! ");
                excluirMenu(clientes);
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
                telaPrincipal();

            }

        } catch (Exception e) {
            cleanConsole();
            System.out.println("Opção inválida");
            msgRetornoPesquisa(clientes);
        }
    }

    private void alterarMenu(ArrayList<Cliente> clientes) {
        System.out.println("Digite o cpf do cliente que deseja alterar: ");
        var cpf = scanner.next();

        if (clientes.isEmpty()) {
            cleanConsole();
            System.out.println("Não há clientes cadastrados!");
            telaPrincipal();
        }

        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                System.out.println("NOME: ");
                var nome = scanner.next();
                System.out.println("IDADE: ");
                var idade = scanner.nextInt();
                System.out.println("DATA DE NASCIMENTO: ");
                var dataNascimento = scanner.next();

                cliente.setNome(nome);
                cliente.setIdade(idade);
                cliente.setDataNascimento(dataNascimento);

                cleanConsole();
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
            telaPrincipal();
        }
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equalsIgnoreCase(cpf)) {
                System.out.println("NOME: " + cliente.getNome().toUpperCase());
                System.out.println("IDADE: " + cliente.getIdade());
                System.out.println("CPF: " + formatarCpf(cliente.getCpf()));
                System.out.println("DATA DE NASCIMENTO: " + formatDataNascimento(cliente.getDataNascimento()));
                System.out.println("\n_____________________________________________________");
                msgRetornoPesquisa(clientes);
            } else {
                cleanConsole();
                System.out.println("CPF não encontrado! ");
                msgRetornoPesquisa(clientes);
            }


        }


    }

    private String formatDataNascimento(String dataNascimento) {

            DateFormat formatoEntrada = new SimpleDateFormat("yyyyMMdd");
            DateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date data = formatoEntrada.parse(dataNascimento);
                return formatoSaida.format(data);
            } catch (ParseException e) {
                e.printStackTrace();
                return dataNascimento; // Retorna a string original se não for possível formatar
            }

    }

    public String formatarCpf(String cpf) {
        // Remover caracteres não numéricos
        cpf = cpf.replaceAll("\\D", "");

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

    private void msgRetornoAlteracao(ArrayList<Cliente> clientes) {
        System.out.println("Digite 1 - para alterar outro cliente.");
        System.out.println("Digite 2 - Voltar ao menu principal.");
        var op = scanner.nextInt();
        try {
            if (op == 1) {
                alterarMenu(clientes);
            } else if (op == 2) {
                telaPrincipal();

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
                telaPrincipal();

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
                telaPrincipal();
            }

        } catch (Exception e) {
            cleanConsole();
            System.out.println("Opção inválida");
            msgRetornoCadastro(clientes);
        }
        telaPrincipal();
    }

    private void cadastroMenu(ArrayList<Cliente> clientes)  {
        System.out.println("NOME: ");
        String nome = scanner.skip("\\R?").nextLine().toUpperCase();
        System.out.println("IDADE: ");
        var idade = scanner.nextInt();
        var cpf = cadastraCpf().formatted("%s.%s.%s-%s");
        System.out.println("DATA DE NASCIMENTO: ");
        var dataNascimento = scanner.next().formatted("%s/%s/%s");

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


    private void cleanConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }

    }



}
