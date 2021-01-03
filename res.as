  .text
  .globl main
main:
  #prologue
  #push
  sub  $sp, $sp, 4
  sw  $fp, 0($sp)

  #push
  sub  $sp, $sp, 4
  sw  $ra, 0($sp)

  move  $fp, $sp
  
  #prologue
#value = value
  li $t0, 1
  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  lw  $t0,  0($sp)  #store
  sw  $t0, -4($fp)
  #store#value = value

#result = value
  li $t0, 1
  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  #pop
  lw $a0, 0($sp)
  addiu $sp, $sp, 4

  jal print
  #push
  sub  $sp, $sp, 4
  sw  $v0, 0($sp)


  li $t0, 1000
  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)


  li $t0, 1
  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  li $t0, 3
  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  #pop
  lw $t1, 0($sp)
  addiu $sp, $sp, 4

  #pop
  lw $t2, 0($sp)
  addiu $sp, $sp, 4

  mul $s0, $t2, $t1
  #push
  sub  $sp, $sp, 4
  sw  $s0, 0($sp)

  lw  $t0,  0($sp)  #store
  sw  $t0, -8($fp)
  #store#result = value

  li $t0, 12
  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  #pop
  lw $a0, 0($sp)
  addiu $sp, $sp, 4

  jal print
  #push
  sub  $sp, $sp, 4
  sw  $v0, 0($sp)


  lw  $t0,  -8($fp)

  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  #pop
  lw $a0, 0($sp)
  addiu $sp, $sp, 4

  jal print
  #push
  sub  $sp, $sp, 4
  sw  $v0, 0($sp)


  lw  $t0,  -4($fp)

  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  li $t0, 1
  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  #pop
  lw $t1, 0($sp)
  addiu $sp, $sp, 4

  #pop
  lw $t2, 0($sp)
  addiu $sp, $sp, 4

  add $s0, $t2, $t1
  #push
  sub  $sp, $sp, 4
  sw  $s0, 0($sp)

  #pop
  lw $a0, 0($sp)
  addiu $sp, $sp, 4

  jal print
  #push
  sub  $sp, $sp, 4
  sw  $v0, 0($sp)


#mips = value
  li $t0, 12
  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  li $t0, 3
  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  #pop
  lw $t1, 0($sp)
  addiu $sp, $sp, 4

  #pop
  lw $t2, 0($sp)
  addiu $sp, $sp, 4

  add $s0, $t2, $t1
  #push
  sub  $sp, $sp, 4
  sw  $s0, 0($sp)

  lw  $t0,  0($sp)  #store
  sw  $t0, -12($fp)
  #store#mips = value

  lw  $t0,  -12($fp)

  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  lw  $t0,  -4($fp)

  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  #pop
  lw $t1, 0($sp)
  addiu $sp, $sp, 4

  #pop
  lw $t2, 0($sp)
  addiu $sp, $sp, 4

  add $s0, $t2, $t1
  #push
  sub  $sp, $sp, 4
  sw  $s0, 0($sp)

  #pop
  lw $a0, 0($sp)
  addiu $sp, $sp, 4

  jal print
  #push
  sub  $sp, $sp, 4
  sw  $v0, 0($sp)


  li $t0, 1
  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)


  li $t0, 3
  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  #pop
  lw $a0, 0($sp)
  addiu $sp, $sp, 4

#pop
  lw $a1, 0($sp)
  addiu $sp, $sp, 4

  jal plus
  #push
  sub  $sp, $sp, 4
  sw  $v0, 0($sp)

  #pop
  lw $a0, 0($sp)
  addiu $sp, $sp, 4

  jal print
  #push
  sub  $sp, $sp, 4
  sw  $v0, 0($sp)

  #pop
  lw $v0, 0($sp)
  addiu $sp, $sp, 4

  #epilogue
  
  move  $sp,  $fp
  #pop
  lw $ra, 0($sp)
  addiu $sp, $sp, 4

  #pop
  lw $fp, 0($sp)
  addiu $sp, $sp, 4

  #epilogue
  jr  $ra

  .globl plus
plus:
  #prologue
  #push
  sub  $sp, $sp, 4
  sw  $fp, 0($sp)

  #push
  sub  $sp, $sp, 4
  sw  $ra, 0($sp)

  move  $fp, $sp
  #push
  sub  $sp, $sp, 4
  sw  $a0, 0($sp)

#push
  sub  $sp, $sp, 4
  sw  $a1, 0($sp)

  #prologue
#spim = value
  li $t0, 1
  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  lw  $t0,  0($sp)  #store
  sw  $t0, -12($fp)
  #store#spim = value

  lw  $t0,  -12($fp)

  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  lw  $t0,  -4($fp)

  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  #pop
  lw $t1, 0($sp)
  addiu $sp, $sp, 4

  #pop
  lw $t2, 0($sp)
  addiu $sp, $sp, 4

  add $s0, $t2, $t1
  #push
  sub  $sp, $sp, 4
  sw  $s0, 0($sp)

  lw  $t0,  -8($fp)

  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  #pop
  lw $t1, 0($sp)
  addiu $sp, $sp, 4

  #pop
  lw $t2, 0($sp)
  addiu $sp, $sp, 4

  add $s0, $t2, $t1
  #push
  sub  $sp, $sp, 4
  sw  $s0, 0($sp)

  #pop
  lw $a0, 0($sp)
  addiu $sp, $sp, 4

  jal print
  #push
  sub  $sp, $sp, 4
  sw  $v0, 0($sp)

  #pop
  lw $v0, 0($sp)
  addiu $sp, $sp, 4

  #epilogue
  #pop
  lw $a0, 0($sp)
  addiu $sp, $sp, 4

#pop
  lw $a1, 0($sp)
  addiu $sp, $sp, 4

  move  $sp,  $fp
  #pop
  lw $ra, 0($sp)
  addiu $sp, $sp, 4

  #pop
  lw $fp, 0($sp)
  addiu $sp, $sp, 4

  #epilogue
  jr  $ra

  .globl print
print:
  #prologue
  #push
  sub  $sp, $sp, 4
  sw  $fp, 0($sp)

  #push
  sub  $sp, $sp, 4
  sw  $ra, 0($sp)

  move  $fp, $sp
  
  #prologue
  li  $v0, 1
  syscall

  li  $v0, 0
  #pop
  lw $v0, 0($sp)
  addiu $sp, $sp, 4

  #epilogue
  
  move  $sp,  $fp
  #pop
  lw $ra, 0($sp)
  addiu $sp, $sp, 4

  #pop
  lw $fp, 0($sp)
  addiu $sp, $sp, 4

  #epilogue
  jr  $ra

