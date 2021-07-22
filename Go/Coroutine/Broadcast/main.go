package main

import (
	"fmt"
	"time"
)

func notify(id int, channel chan int) {
	<-channel //接收到数据或通道关闭时退出阻塞
	fmt.Printf("%d receive a message.\n", id)
}
func broadcast(channel chan int) {
	fmt.Printf("Broadcast:\n")
	close(channel) //关闭通道
}
func main() {
	channel := make(chan int, 1)
	for i := 0; i < 10; i++ {
		go notify(i, channel) //开10个协程，等待channel信息，都处于阻塞状态
	}
	go broadcast(channel)
	time.Sleep(time.Second) //没有sleep会导致go未执行直接终止
}
