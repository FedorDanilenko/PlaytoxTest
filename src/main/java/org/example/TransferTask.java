package org.example;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
class TransferTask implements Runnable {
    private final Account from;
    private final Account to;
    private final Random random = new Random();

    public TransferTask(Account from, Account to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        for (int i = 0; i < 30; i++) {
            int amount = random.nextInt(1000); // Сумма для перевода от 0 до 999
            synchronized (from) {
                synchronized (to) {
                    if (from.getMoney() >= amount) {
                        from.withdraw(amount);
                        to.deposit(amount);
                        log.info("Transaction: {} -> {}, Amount: {}", from.getID(), to.getID(), amount);
                    }
                }
            }
            try {
                Thread.sleep(random.nextInt(1001) + 1000); // Засыпаем от 1000 до 2000 мс
            } catch (InterruptedException e) {
                log.error("Thread interrupted", e);
            }
        }
    }
}

