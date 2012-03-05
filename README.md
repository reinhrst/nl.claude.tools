# nl.claude.tools

(Clojure) tools by Claude. Contains

 * nl.claude.tools.dct: One dimensional Discrete Cosine Transform functions in clojure, including test data. Note: DCT is a prime candidate to be paralellized, and clojure is good at this. However this first version doesn't do anything parallel. In addition, this version is EXTREMELY SLOW, probably for now you're better off using JTransforms. This version is for now just for reference purposes.
 * nl.claude.tools.timed-sequence: Make a sequence timed. Provided a sequence, produces a new (lazy) sequence where a new item becomes available every x seconds.
 * misc: other stuff, currenly containing just the HVR-2200 PAL support patch

## Usage

### DCT
Put the jar-file in your java extensions directory (on OSX for example /Library/Java/Extensions). Next just call

(use 'nl.claude.tools.dct)
(dct-ii [1 2 3 4 5])
(dct-iii [2 0 0 0])

These are one dimensional dct operations. dct is a synonym for dct-ii, idct is a synonym for dct-iii

EDIT: dct / idct are no longer synonyms but use the JTransform library instead because (for now??, until I fix this :)) this library is about a million times faster :). dct-ii and dct-iii can still be used as reference implementations.

### Timed sequence
Has one function: timed-sequence. This takes a sequence, and a delay. The function makes a new item of the sequence available every "delay" seconds. In the meantime, it sleeps. Note that the first item only becomes available after the first "delay" interval, and counting starts as soon as the timed-sequence is created.

(use 'nl.claude.tools.timed-sequence)
(let [counter (timed-sequence (range) 1/2)]
  (doall (take 5 (map #(do (println %) (flush)) counter))))

Works even with delays of less than 1/1000 second. However, because java time only has milisecond resolution, a timing of 0.0001 means that every 1ms a burst of 10 items becomes available. Probably there is something better to use than java time, but this suits my needs

### HVR-2200 patch
See the misc directory. Patches the Hauppauge HVR-2200 v4l2 drivers to work with the PAL analog signal. This patch is adapted from http://thread.gmane.org/gmane.linux.drivers.video-input-infrastructure/26212/focus=26524 to a newer kernel version. Also see http://www.mythtvtalk.com/you-waiting-hvr-2200-2250-linux-driver-10906-6/ and http://linuxtv.org/wiki/index.php/Hauppauge_WinTV-HVR-2200 .  


## License

Copyright (C) 2012 Claude (Reinoud Elhorst)

Distributed under the Eclipse Public License, the same as Clojure. The HVR-2200 patch has its own license, probably GPL, see the mentioned urls for more info.
