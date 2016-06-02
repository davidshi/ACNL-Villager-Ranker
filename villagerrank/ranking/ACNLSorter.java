/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package villagerrank.ranking;

import java.util.LinkedList;

/**
 *
 * @author owner
 */
public class ACNLSorter {

    private Villager[] villagerArray;
    private LinkedList<LinkedList<Villager>> part1 = new LinkedList<>();
    private LinkedList<LinkedList<Villager>> part2 = new LinkedList<>();
    
    private LinkedList<Villager> currentList1 = new LinkedList<>();
    private LinkedList<Villager> currentList2 = new LinkedList<>();
    private LinkedList<Villager> mergedList = new LinkedList<>();
    
    private boolean hasClearedRound1;
    private boolean isWorkingOnPart1 = true;

    public ACNLSorter(Villager[] villagerArray) {
        this.villagerArray = villagerArray;
    }
    
    /*[10:03:33 PM] David: hm idk about the heap part, but can't i just store it in a list
[10:03:37 PM] David: let's say it's like
[10:03:45 PM] Rahul: well the idea was to not have to go through a 2nd round
[10:03:58 PM] David: list: [a,b] [c,d]
list2:
[10:04:04 PM] David: round 1:
[10:04:16 PM] David: list: [c,d]
list2: [b,a]
[10:04:17 PM] David: round 2:
[10:04:25 PM] David: list: 
list2: [b,a] [c,d]
[10:04:31 PM] David: since list is empty, start phase2
[10:04:36 PM] David: where we select 2 entries
[10:04:40 PM] David: from lsit2 at a time
[10:04:41 PM] David: and merge them!
[10:04:51 PM] David: put them back into list1
[10:05:00 PM] David: if list2 is empty, go do phase2 with phase1*/
/*
    public Villager[] divideIntoPairs(Villager[] list) {
        if (list.length < 2) {
            part1.add(list);
            return list;
        }

        Villager[] subArrayBeg = new Villager[list.length / 2];
        Villager[] subArrayEnd = new Villager[list.length - subArrayBeg.length];

        System.arraycopy(list, 0, subArrayBeg, 0, subArrayBeg.length);
        System.arraycopy(list, subArrayBeg.length, subArrayEnd, 0, subArrayEnd.length);

        divideIntoPairs(subArrayBeg);
        divideIntoPairs(subArrayEnd);
        
        return list;
    }*/
    
    /**
     * Start part1 with pairs:
     * i.e. [a,b] [c,d] [e,f] [g,h]
     */
    public void initiateList1() {
        for (int i = 0; i < villagerArray.length - 1; i += 2) {
            LinkedList<Villager> villagerList = new LinkedList<>();
            villagerList.add(villagerArray[i]);
            if (villagerArray.length > i) {
                villagerList.add(villagerArray[i + 1]);
            }
            part1.add(villagerList);
        }
    }

    public LinkedList<Villager> getNextVillagerList() {
        if (part1.isEmpty() && part2.isEmpty()) {
            // maybe throw exception for no data
            return null;
        }
        
        
        // In the first round we just sort the pairs
        // Ex: [joe, bob], [billy, bob2]
        // Round 1: Return [joe, bob]
        // Round 2: Return [billy, bob2]
        if (!hasClearedRound1) {
            if (!part1.isEmpty()) {
                LinkedList<Villager> list = part1.pop();

                // if empty, just add the list, we don't need to display
                // Can just be an if here, we shouldn't get more than 1
                while (list.get(1) == null) {
                    part2.add(list);
                    list = part1.pop();
                }

                return list;
            } else {
                // Set it to work on part2
                isWorkingOnPart1 = false;
                hasClearedRound1 = true;
                return getNextVillagerList(); // hopefully won't break
            }
        }
        
        // At this point, hasClearedRound1 should never be false
        // After round 1, we "mergesort them" using the "merge" stage
        
        // [worse, better]
        // 1: 
        // 2: [bam, bluebear], [charlise, aurora], [chief, poppy], [kyle, jay]
        
        LinkedList<LinkedList<Villager>> listToUse = isWorkingOnPart1 ? part1: part2;
        LinkedList<LinkedList<Villager>> otherList = isWorkingOnPart1 ? part2: part1;
        
        // if list1 and list2 empty, then put merged onto saved
        // if list1 empty, then add list2 to merged, put merged onto saved
        // if list2 empty, then add list1 to merged, put merged onto saved
        boolean needsToSelectNext = false;
        if (currentList1.isEmpty() && currentList2.isEmpty()) {
            needsToSelectNext = true;
        } else if (currentList1.isEmpty()) {
            mergedList.addAll(currentList2);
            needsToSelectNext = true;
        } else if (currentList2.isEmpty()) {
            mergedList.addAll(currentList1);
            needsToSelectNext = true;
        }
        
        if (needsToSelectNext && listToUse.size() < 2) {
            
        }
        
        // curr1: []
        // curr2: []
        // MergedList: [charlise, bam, aurora, bluebear], [kyle, jay, chief, poppy]
        
        if (needsToSelectNext) {
            otherList.add(mergedList);
            mergedList.clear();
            
            // if there's only 1 list left then we can just add that to mergedList and stop
            if (listToUse.isEmpty()) {
                // switch modes here
            } else if (listToUse.size() == 1) {
                otherList.add(listToUse.pop());
                // switch modes here
            } else {
                currentList1 = listToUse.pop();
                currentList2 = listToUse.pop();
            }
        }
        
        LinkedList<Villager> selections = new LinkedList<>();
        selections.add(currentList1.pop());
        selections.add(currentList2.pop());

        return getNextVillagerList(); // hopefully won't break
    }
}
