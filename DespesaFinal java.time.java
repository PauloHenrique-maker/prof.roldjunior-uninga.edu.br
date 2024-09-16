
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Despesa {
    private String descricao;
    private double valor;
    private boolean situacao;
    private LocalDate data;
    private String tipo;

    public Despesa(String descricao, double valor, String tipo, LocalDate data) {
        this.descricao = descricao;
        this.valor = valor;
        this.situacao = false;
        this.data = data;
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public boolean isSituacao() {
        return conciliada;
    }

    public LocalDate getData() {
        return data;
    }

    public String getTipo() {
        return tipo;
    }

    public void conciliar() {
        this.situacao = true;
    }

    @Override
    public String toString() {
        return "Despesa: " + descricao + ", Tipo: " + tipo + ", Valor: R$" + String.format("%.2f", valor) + ", Data: " + data + ", situacao: " + (situacao ? "Sim" : "Não");
    }
}

class Pagamento {
    private Despesa despesa;
    private double valorPago;
    private LocalDate dataPagamento;

    public Pagamento(Despesa despesa, double valorPago, LocalDate dataPagamento) {
        this.despesa = despesa;
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public double getValorPago() {
        return valorPago;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void situacaodepesa() {
        if (valorPago >= despesa.getValor()) {
            despesa.conciliar();
        }
    }

    @Override
    public String toString() {
        return "Pagamento de R$" + String.format("%.2f", valorPago) + " para " + despesa.getDescricao() + " em " + dataPagamento;
    }
}

class SistemaControleDespesas {
    private List<Despesa> despesas;
    private List<Pagamento> pagamentos;
    private List<String> tiposDeDespesa;
    private List<String> usuarios;

    public SistemaControleDespesas() {
        this.despesas = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
        this.tiposDeDespesa = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    // Gerenciamento de Tipos de Despesa
    public void adicionarTipoDespesa(String tipo) {
        if (!tiposDeDespesa.contains(tipo)) {
            tiposDeDespesa.add(tipo);
            System.out.println("Tipo de despesa adicionado: " + tipo);
        } else {
            System.out.println("Tipo de despesa já existe.");
        }
    }

    public void removerTipoDespesa(String tipo) {
        if (tiposDeDespesa.remove(tipo)) {
            System.out.println("Tipo de despesa removido: " + tipo);
        } else {
            System.out.println("Tipo de despesa não encontrado.");
        }
    }

    public void exibirTiposDespesas() {
        System.out.println("\nTipos de Despesa:");
        for (String tipo : tiposDeDespesa) {
            System.out.println(tipo);
        }
    }

    // Gerenciamento de Usuários
    public void adicionarUsuario(String usuario) {
        if (!usuarios.contains(usuario)) {
            usuarios.add(usuario);
            System.out.println("Usuário adicionado: " + usuario);
        } else {
            System.out.println("Usuário já existe.");
        }
    }

    public void removerUsuario(String usuario) {
        if (usuarios.remove(usuario)) {
            System.out.println("Usuário removido: " + usuario);
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    public void exibirUsuarios() {
        System.out.println("\nUsuários:");
        for (String usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    // Entrar Despesa
    public void adicionarDespesa(String descricao, double valor, String tipo, LocalDate data) {
        Despesa despesa = new Despesa(descricao, valor, tipo, data);
        despesas.add(despesa);
        System.out.println("Despesa adicionada: " + despesa);
    }

    // Anotar Pagamento
    public void registrarPagamento(String descricao, double valorPago, LocalDate dataPagamento) {
        for (Despesa despesa : despesas) {
            if (despesa.getDescricao().equalsIgnoreCase(descricao) && !despesa.isConciliada()) {
                Pagamento pagamento = new Pagamento(despesa, valorPago, dataPagamento);
                pagamentos.add(pagamento);
                pagamento.conciliarDespesa();
                System.out.println("Pagamento registrado: " + pagamento);
                return;
            }
        }
        System.out.println("Despesa não encontrada ou já conciliada.");
    }

    // Listar Despesas em Aberto no Período
    public void listarDespesasEmAberto(LocalDate inicio, LocalDate fim) {
        System.out.println("\nDespesas em Aberto no Período:");
        for (Despesa despesa : despesas) {
            if (!despesa.isConciliada() && despesa.getData().compareTo(inicio) >= 0 && despesa.getData().compareTo(fim) <= 0) {
                System.out.println(despesa);
            }
        }
    }

    // Listar Despesas Pagas no Período
    public void listarDespesasPagas(LocalDate inicio, LocalDate fim) {
        System.out.println("\nDespesas Pagas no Período:");
        for (Pagamento pagamento : pagamentos) {
            if (pagamento.getDataPagamento().compareTo(inicio) >= 0 && pagamento.getDataPagamento().compareTo(fim) <= 0) {
                System.out.println(pagamento);
            }
        }
    }
}

public class SistemaDeControleDeDespesas {
    public static void main(String[] args) {
        SistemaControleDespesas sistema = new SistemaControleDespesas();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\nSistema de Controle de Despesas:");
            System.out.println("1. Entrar Despesa");
            System.out.println("2. Anotar Pagamento");
            System.out.println("3. Listar Despesas em Aberto no Período");
            System.out.println("4. Listar Despesas Pagas no Período");
            System.out.println("5. Gerenciar Tipos de Despesa");
            System.out.println("6. Gerenciar Usuários");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1:
                    System.out.print("Digite a descrição da despesa: ");
                    String descricaoDespesa = scanner.nextLine();
                    System.out.print("Digite o valor da despesa: ");
                    double valorDespesa = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Digite o tipo da despesa: ");
                    String tipoDespesa = scanner.nextLine();
                    System.out.print("Digite a data da despesa (AAAA-MM-DD): ");
                    LocalDate dataDespesa = LocalDate.parse(scanner.nextLine());
                    sistema.adicionarDespesa(descricaoDespesa, valorDespesa, tipoDespesa, dataDespesa);
                    break;
                case 2:
                    System.out.print("Digite a descrição da despesa para pagamento: ");
                    String descricaoPagamento = scanner.nextLine();
                    System.out.print("Digite o valor pago: ");
                    double valorPago = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Digite a data do pagamento (AAAA-MM-DD): ");
                    LocalDate dataPagamento = LocalDate.parse(scanner.nextLine());
                    sistema.registrarPagamento(descricaoPagamento, valorPago, dataPagamento);
                    break;
                case 3:
                    System.out.print("Digite a data de início do período (AAAA-MM-DD): ");
                    LocalDate inicioAberto = LocalDate.parse(scanner.nextLine());
                    System.out.print("Digite a data de fim do período (AAAA-MM-DD): ");
                    LocalDate fimAberto = LocalDate.parse(scanner.nextLine());
                    sistema.listarDespesasEmAberto(inicioAberto, fimAberto);
                    break;
                case 4:
                    System.out.print("Digite a data de início do período (AAAA-MM-DD): ");
                    LocalDate inicioPago = LocalDate.parse(scanner
