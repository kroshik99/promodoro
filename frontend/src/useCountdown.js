import { useEffect, useRef, useState } from 'react'

export function useCountdown(session, onDue) {
  const [now, setNow] = useState(Date.now())
  const firedForSessionId = useRef(null)

  useEffect(() => {
    if (!session) return
    const id = setInterval(() => setNow(Date.now()), 1000)
    return () => clearInterval(id)
  }, [session])

  const startedAt = session ? new Date(session.startedAt).getTime() : null
  const endTarget = session ? startedAt + session.plannedMinutes * 60000 : null
  const remainingSeconds = session ? Math.round((endTarget - now) / 1000) : null
  const overdue = session ? remainingSeconds < 0 : false

  useEffect(() => {
    if (session && overdue && onDue && firedForSessionId.current !== session.id) {
      firedForSessionId.current = session.id
      onDue()
    }
  }, [session, overdue, onDue])

  if (!session) {
    return { text: '--:--', overdue: false }
  }

  const abs = Math.abs(remainingSeconds)
  const m = Math.floor(abs / 60).toString().padStart(2, '0')
  const s = Math.floor(abs % 60).toString().padStart(2, '0')

  return { text: (overdue ? '-' : '') + m + ':' + s, overdue }
}
