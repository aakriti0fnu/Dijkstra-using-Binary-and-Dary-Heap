#!/usr/local/bin/tclsh

set numChild 7
set directories [glob -type d ED*]
#puts $directories 
foreach directory $directories {
	cd $directory 
        puts "In Directory: $directory"
#	exec rm -rf output_stats_db.txt
#	exec rm -rf output_stats_$numChild.txt
	exec javac AllPairShortestPath.java
	set graphs [glob -type f *.gph]
	
#	foreach graph $graphs {
#		puts "Executing $graph in db"
#		exec echo "For Graph: $graph in db" >> output_stats_db.txt
#		exec java AllPairShortestPath db  < $graph 2>> output_stats_db.txt
#		exec echo "---------" >> output_stats_db.txt 
#	}
	foreach graph $graphs {
                puts "Executing $graph in dd with numChild: $numChild"
                exec echo "For Graph: $graph in dd" >> output_stats_$numChild.txt
                exec java AllPairShortestPath dd $numChild < $graph 2>> output_stats_$numChild.txt
                exec echo "---------" >> output_stats_$numChild.txt
        }
	cd ../

}

