  .text
  .globl function_digitsRecurse
function_digitsRecurse:
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
#trimmed = value
  lw  $t0,  -4($fp)

  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  li $t0, 10
  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  #pop
  lw $t1, 0($sp)
  addiu $sp, $sp, 4

  #pop
  lw $t2, 0($sp)
  addiu $sp, $sp, 4

  div $s0, $t2, $t1
  #push
  sub  $sp, $sp, 4
  sw  $s0, 0($sp)

  lw  $t0,  0($sp)  #store
  sw  $t0, -12($fp)
  #store#trimmed = value

    lw  $t0,  -12($fp)

  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  li $t0, 0
  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  #pop
  lw $t1, 0($sp)
  addiu $sp, $sp, 4

  #pop
  lw $t2, 0($sp)
  addiu $sp, $sp, 4

  seq $s0, $t2, $t1
  #push
  sub  $sp, $sp, 4
  sw  $s0, 0($sp)


  #pop
  lw $s0, 0($sp)
  addiu $sp, $sp, 4

  beq $s0,  $zero,  false1
true1:
      lw  $t0,  -8($fp)

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


    j end1
false1:
      lw  $t0,  -12($fp)

  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)


  lw  $t0,  -8($fp)

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
  lw $a1, 0($sp)
  addiu $sp, $sp, 4

#pop
  lw $a0, 0($sp)
  addiu $sp, $sp, 4

  jal function_digitsRecurse
  #push
  sub  $sp, $sp, 4
  sw  $v0, 0($sp)


end1:
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

  .globl function_digits
function_digits:
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

  #prologue
  lw  $t0,  -4($fp)

  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)


  li $t0, 0
  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  #pop
  lw $a1, 0($sp)
  addiu $sp, $sp, 4

#pop
  lw $a0, 0($sp)
  addiu $sp, $sp, 4

  jal function_digitsRecurse
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

  move  $sp,  $fp
  #pop
  lw $ra, 0($sp)
  addiu $sp, $sp, 4

  #pop
  lw $fp, 0($sp)
  addiu $sp, $sp, 4

  #epilogue
  jr  $ra

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
#result = value
  li $t0, 0
  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  #pop
  lw $a0, 0($sp)
  addiu $sp, $sp, 4

  jal function_digits
  #push
  sub  $sp, $sp, 4
  sw  $v0, 0($sp)

  lw  $t0,  0($sp)  #store
  sw  $t0, -4($fp)
  #store#result = value

  lw  $t0,  -4($fp)

  #push
  sub  $sp, $sp, 4
  sw  $t0, 0($sp)

  #pop
  lw $a0, 0($sp)
  addiu $sp, $sp, 4

  jal function_print
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

  .globl function_print
function_print:
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

