#### 1. 谈谈你理解的 HashMap，讲讲其中的 get put 过程

    HashMap的结构是数组table + 链表Entry。
    put过程：
    (1) 对key就行hash；
    (2) 通过hash定位数据应该在数组中的下标index，获取链表Entry；
    (3) 如果Entry为null，需要新增一个Entry，指向index这个位置；
    (4) Entry不为null，遍历Entry，如果找到key相同，hash相同，则覆盖原值，否则新建一个Entry节点，指向index位置，next指向之前的Entry节点。
    get过程：
    (1) 对key进行hash；
    (2) 通过hash获取到在数组中的下标index，获取到链表Entry；
    (3) 如果Entry为null，直接返回null；
    (4) Entry不为null，遍历Entry，如果key相同且hash相同，返回Entry的value。
    
#### 2. 1.8 做了什么优化？

    1.7的缺点：
        当Hash冲突严重的时候，在桶上形成的链表会越来越长，查询的效率极低，时间复杂度退化到O(N)。
        
    1.8优化：
        1.8存储过程类似上面步骤，但是存储结构有变化，增加了一个链表长度的阈值字段，值为8，如果链表长度超过8，就将链表转为红黑树结构。

#### 3. HashMap是线程安全的嘛？不安全会导致哪些问题？
    
    HashMap是非线程安全的。
    高并发的时候会形成环形链表，这样遍历的时候会导致死循环。
    
#### 4. 有没有线程安全的并发容器？
    
    ConcurrentHashMap。

#### 5. ConcurrentHashMap 是如何实现的？ 1.7、1.8 实现有何不同？为什么这么做？

    1.7采用分段锁。
    put过程：
    (1) 获取Segment锁，如果没有获取到，自旋等待，确保能获取到锁；
    (2) 通过key的hash定位到当前Segment下table的Entry；
    (3) Entry为null，新建一个加入到table下；
    (4) 不为null，遍历找到key相同hash相同的Entry，返回Entry的value，没找到新建一个Entry，加到table下，next指向之前的Entry；
    (5) 释放锁。
    get过程：
    (1) 通过key的hash定位到Segment；
    (2) 在Segment下通过hash定位到Entry，遍历Entry，如果key相同并且hash相同，返回value。
    
    1.8采用CAS + synchronized。
    同样是对查询效率做了优化。和HashMap一样，当链表长度大于阈值的时候转为红黑树保存数据。
    
    好处：
    红黑树保证了查询效率时间复杂度O(logN)。
    