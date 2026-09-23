package br.com.zenon;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FraudAnalyzer {

    private final List<Transaction> transactions;

    public FraudAnalyzer(List<Transaction> transactions){
        Objects.requireNonNull(transactions);
        this.transactions = transactions;
    }

    public long countFrauds(){
        return fraudStream()
                .count();
    }


    public List<BigDecimal> findHighestValueFraudAmounts(int limit) {
        return highValueStream()
                .map(Transaction::amount)
                .limit(limit)
                .toList();

    }

    public List<String> findTopSuspiciousClients(int limit) {
        return highValueStream()
                .map(transaction -> transaction.origin().name())
                .limit(limit)
                .toList();
    }

    private Stream<Transaction> highValueStream() {
        return fraudStream()
                .sorted(Comparator.comparing(Transaction::amount).reversed());
    }

    public BigDecimal calculateTotalFraudLoss() {
        return fraudStream()
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<TransactionType, Long> countFraudsByType() {
        return fraudStream()
                .collect(Collectors.groupingBy(Transaction::type, Collectors.counting()));
    }

    private Stream<Transaction> fraudStream() {
        return transactions
                .stream()
                .filter(Transaction::isFraud);
    }
}
