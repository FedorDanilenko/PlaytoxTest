package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class StarterWorker implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        // Генерируем случайное число счетов от 4 до 10
        Random random = new Random();
        int numAccounts = random.nextInt(7) + 4;

        // Создаем список счетов
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < numAccounts; i++) {
            accounts.add(new Account(Integer.toString(i + 1), 10000));
        }

        int totalBalance = accounts.stream()
            .mapToInt(Account::getMoney)
            .sum();
        log.info("Total balance before transactions: {}", totalBalance);

        // Создаем потоки
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < numAccounts / 2; i++) {
            executorService.execute(new TransferTask(accounts.get(i), accounts.get(i + numAccounts / 2)));
        }

        // Ждем завершения транзакций
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("Thread interrupted", e);
            }
        }
        log.info("All transactions completed.");
        log.info("Balances of all accounts:");
        accounts.forEach(account -> log.info("Account " + account.getId() +
                ": Balance = " + account.getMoney()));
        totalBalance = accounts.stream()
            .mapToInt(Account::getMoney)
            .sum();
        log.info("Total balance after transactions: " + totalBalance);

    }
}
