.data
x: .word 0
y: .word 0
a: .byte 0
b: .byte 0
c: .byte 0
msg1: .asciiz "Please select a piece"
msg2: .asiicz "The Piece you selected was"
.text

li $v0, 4
la $a0, msg1
syscall
li $v0, 12
syscall
sb $v0, x


li $v0, 4
la $a0, msg2
syscall

li $v0, 4
la $a0, x
syscall
li $v0, 10
syscall
