package main

import (
	log "github.com/sirupsen/logrus"
	"net"
	"os"
)

func InitLog(){
	log.SetFormatter(&log.TextFormatter{
		ForceColors:               true,
		EnvironmentOverrideColors: true,
		TimestampFormat:           "2006-01-02 15:04:05",
		FullTimestamp:             true,
	})
}

func checkError(err error){
	if  err != nil {
		log.Error("Error: %s", err.Error())
		os.Exit(1)
	}
}

func recvUDPMsg(conn *net.UDPConn){
	var buf [50]byte
	n, raddr, err := conn.ReadFromUDP(buf[0:])
	if err != nil {
		return
	}

	log.Info("msg is ", string(buf[0:n]))

	_, err = conn.WriteToUDP([]byte("[server]:"+string(buf[0:n])), raddr)
	checkError(err)
}

func main() {
	InitLog()
	log.Info("start server")

	udp_addr,err := net.ResolveUDPAddr("udp",":20002")
	checkError(err)

	conn,err := net.ListenUDP("udp",udp_addr)
	defer conn.Close()
	checkError(err)

	for true{
		recvUDPMsg(conn)
	}
}

