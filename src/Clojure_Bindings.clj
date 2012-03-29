(ns Clojure-Bindings)
(import '(synthseq.synthesizer Synthesizer)
        '(synthseq.playables Clip)
        '(synthseq.playables.readables ReadableSound)
        '(synthseq.playables.readables.waveforms SawWave SineWave PulseWave WhiteNoise)
        '(synthseq.playables.readables.filters StringInst)
        '(synthseq.playables.readables.operations Adder Multiplier Clipping))
(defn test-success [] (println "success"))
(defn saw [freq] (SawWave. freq))
(defn sine [freq] (SineWave. freq))
(defn pulse 
  ([freq] (PulseWave. freq))
  ([freq ratio] (PulseWave. freq ratio)))
(defn white-noise [] (WhiteNoise.))
(defn stringinst [freq] (StringInst. freq)) 
(defn add 
  ([readables] (Adder. readables)))
(defn addm 
  ([& readables] (Adder. readables)))
(defn mult [readable mult] (Multiplier. readable mult))
(defn clip [readable maxVal] (Clipping. readable maxVal))
(def synth (Synthesizer/getInstance))
(defn show-vis [] (.showVisualizer synth))
(defn kill [] (.kill synth))
(defn testSynthAutoKill [] (.size synth))
