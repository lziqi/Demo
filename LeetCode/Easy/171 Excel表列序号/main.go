package main

import "fmt"

func titleToNumber(columnTitle string) int {
	res := 0

	multiple := 1
	for i := len(columnTitle) - 1; i >= 0; i-- {
		temp := columnTitle[i] - 'A' + 1
		res = res + int(temp)*multiple
		multiple *= 26
	}

	return res
}

func main() {
	columnTitle := "AB"
	fmt.Println(titleToNumber(columnTitle))
}
