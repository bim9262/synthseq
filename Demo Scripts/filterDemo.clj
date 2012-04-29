(def filter (variable 0.5))
(bind-slider "/1/fader5" filter)

(def hfilter (variable 0.5))
(def lfilter (variable 0.5))

(bind-slider "/1/fader4" hfilter)
(bind-slider "/1/fader3" lfilter)

(play (HPF (LPF (mult (load-clip "/home/john/Desktop/partyrock.wav") (mult filter 10.0)) lfilter) hfilter))

(def beats (map load-clip (list-files "/home/john/Desktop/Beats/misc")))

(def beats (map load-clip (list-files "/home/john/Desktop/Beats/drums3/tape clean")))


(map println (list-files "/home/john/Desktop/Beats/waves"))


(def m (metronome 480))

(bind-touch 
(zipmap 
(gen-binds "/2/push!1" (range 1 17))
(for [x (range 1 17)] (fn [] 
            (do-at (m (+ (m) 1)) 
                   #(play (nth beats x)))))))

(bind-touch 
(zipmap 
(gen-binds "/2/push!1" (range 1 17))
beats))

(def thx (load-clip "/home/john/Desktop/thx.wav"))


(defn rhythm []
(play (nth beats 1))
(do-at (m (+ (m) 4)) rhythm))
(rhythm)
(kill-tasks)


(bind-touch "/2/push16" (mult thx 7.0))