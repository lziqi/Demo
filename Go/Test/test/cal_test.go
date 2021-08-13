package main

import (
	"../fun"
	"testing"
)

func TestName(t *testing.T) {
	if ans := fun.Add(1,2);ans!=3{
		t.Error("add (1,2) should be 3")
	}
}
