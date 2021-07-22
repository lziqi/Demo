package main

import (
	"fmt"
	"sync"
)

func main() {
	//计数的信号量
	var wg sync.WaitGroup
	//初始设置为2
	wg.Add(2)
	go func() {
		defer wg.Done() //线程执行结束后让wg-1
		for i := 0; i < 100; i++ {
			fmt.Printf("Hello,Go.This is %d\n", i)
		}
	}()
	go func() {
		defer wg.Done()
		for i := 0; i < 100; i++ {
			fmt.Printf("Hello,World.This is %d\n", i)
		}
	}()

	//wait()表示如果wg计数器大于0，就会一直阻塞，没有计数器会直接中止执行，不等待2哥goroutine结束
	wg.Wait()
}
