package com.avocado.payment.annotation;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

@Aspect
@RequiredArgsConstructor
public class DistributedLockAspect {
    private final RedissonClient redissonClient;

    @Around(value = "@annotation(distributedLock)")
    public Object around(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        String lockName = distributedLock.key().name();
        RLock rLock = redissonClient.getLock(lockName);

        try {
            // Lock 획득 시도
            boolean isSuccess = rLock.tryLock(5, 5, TimeUnit.SECONDS);

            if (isSuccess) {
                return joinPoint.proceed();
            } else {
                // Lock 획득 실패 시 예외 던지기
                throw new RuntimeException("Failed to get lock");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("InterruptedException under getting lock");
        } finally {
            rLock.unlock();
        }
    }
}
