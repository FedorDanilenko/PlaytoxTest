package org.example;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
class TransferTask implements Runnable {
    private final Account from;
    private final Account to;
    private final Lock lock = new ReentrantLock();
    private final Random random = new Random();
    private int num;

    public TransferTask(Account from, Account to,
                        int num) {
        this.from = from;
        this.to = to;
        this.num = num;
    }

    @Override
    public void run() {
        int amount = random.nextInt(10000); // Сумма для перевода от 0 до 9999

        if (lock.tryLock()) {
            try {
                if (from.getMoney() >= amount) {
                    from.withdraw(amount);
                    to.deposit(amount);
                    log.info("Transaction №" + num +
                        ": " + from.getId() + " -> " + to.getId() +
                        ", Amount: " + amount +
                        ". " + from.getId() + " balance: " + from.getMoney() +
                        ". " + to.getId() + " balance: " + to.getMoney());
                } else {
                    log.warn("Account " + from.getId() + " doesn't have enough money for Transaction " +
                        "№" + num + ". " + from.getId() + " balance: " + from.getMoney() +
                        ". Amount: " + amount);
                }
            } finally {
                lock.unlock();
            }
        } else {
            log.warn("Could not acquire lock for transaction from " + from.getId() + " to " + to.getId());
        }

        try {
            Thread.sleep(random.nextInt(1001) + 1000); // Засыпаем от 1000 до 2000 мс
        } catch (InterruptedException e) {
            log.error("Thread interrupted", e);
        }
    }
}
