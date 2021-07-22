package main

import (
	"fmt"
	"time"
)

func foreword() {
	//发送数据：写channel1<- data
	//接收数据：读data := <- channel1

	//var send chan<- int //只能发送
	//var receive <-chan int //只能接收
}

type Person struct {
	name    string
	age     uint8
	address Address
}

type Address struct {
	city     string
	district string
}

func SendMessage(person *Person, channel chan Person) {
	go func(person *Person, channel chan Person) {
		fmt.Printf("%s send a message.\n", person.name)
		channel <- *person
		for i := 0; i < 5; i++ {
			channel <- *person
		}
		close(channel)
		fmt.Println("channel is closed.")
	}(person, channel)
}
func main() {
	channel := make(chan Person, 1)
	harry := Person{"Harry", 30, Address{"London", "Oxford"}}

	go SendMessage(&harry, channel)
	data := <-channel
	fmt.Printf("main goroutine receive a message from %s.\n", data.name)
	for {
		i, ok := <-channel
		time.Sleep(time.Second)
		if !ok {
			fmt.Println("channel is empty.")
			break
		} else {
			fmt.Printf("receive %s\n", i.name)
		}
	}
}
