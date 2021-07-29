package main

import (
	"fmt"
	"net"
	"os"
)

func main() {
	conn, err := net.Dial("udp", "lziqi.top:20002")
	defer conn.Close()
	if err != nil {
		os.Exit(1)
	}

	conn.Write([]byte("123!"))

	fmt.Println("send msg")

	var msg [20]byte
	n,_ := conn.Read(msg[0:])

	fmt.Println("msg is", string(msg[0:n]))
}
