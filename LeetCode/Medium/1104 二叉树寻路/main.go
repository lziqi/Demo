package main

import (
	"fmt"
	"math"
)

/*判断奇偶，奇返回true*/
func odd(x int) bool {
	if x&0x1 == 0 {
		return false
	}
	return true
}

func xy2num(x int, y int) int {
	if x == 1 && y == 0 {
		return 1
	}

	if odd(int(x)) {
		return int(math.Pow(2, float64(x-1)) + float64(y))
	} else {
		return int(math.Pow(2, float64(x)) - float64(y) - 1)
	}
}

func num2xy(num int) (int, int) {
	x, y := 0.0, 0
	for int(math.Pow(2, x)) <= num {
		x++
	}

	if odd(int(x)) {
		y = num - int(math.Pow(2, x-1))
	} else {
		y = int(math.Pow(2, x)) - num - 1
	}

	return int(x), y
}

func pathInZigZagTree(label int) []int {
	res := make([]int, 0)
	x, y := num2xy(label)

	for ; x > 0; x,y = x - 1,y/2 {
		res = append(res, xy2num(x, y))
	}

	for i, j := 0, len(res)-1; i < j; i, j = i+1, j-1 {
		res[i], res[j] = res[j], res[i]
	}
	return res
}

func main() {
	label := 13
	fmt.Println(pathInZigZagTree(label))
}
