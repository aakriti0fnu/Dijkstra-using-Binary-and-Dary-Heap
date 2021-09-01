#!/usr/local/bin/tclsh


array set inputs {
200	3980
300	8970
400	15960
500	24950
600	35940
700	48930
800	63920
900	80910
1000	99900
}

foreach input [array names inputs] {
       catch {exec ../generate.sh  1 rn $input $inputs($input) 2 25}
}
