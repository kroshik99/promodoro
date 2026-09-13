let ctx = null

function getContext() {
  if (!ctx) {
    const AudioCtx = window.AudioContext || window.webkitAudioContext
    ctx = new AudioCtx()
  }
  return ctx
}

function tone(context, freq, startTime, duration) {
  const osc = context.createOscillator()
  const gain = context.createGain()

  osc.type = 'sine'
  osc.frequency.value = freq
  gain.gain.setValueAtTime(0, startTime)
  gain.gain.linearRampToValueAtTime(0.18, startTime + 0.02)
  gain.gain.exponentialRampToValueAtTime(0.0001, startTime + duration)

  osc.connect(gain)
  gain.connect(context.destination)

  osc.start(startTime)
  osc.stop(startTime + duration)
}

export function playChime() {
  try {
    const context = getContext()
    if (context.state === 'suspended') {
      context.resume()
    }
    const now = context.currentTime
    tone(context, 587.33, now, 0.35) // D5
    tone(context, 880, now + 0.14, 0.4) // A5
  } catch {
    // Web Audio unavailable — fail silently, the visual state change still shows.
  }
}
