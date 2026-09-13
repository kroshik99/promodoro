import { useCallback, useEffect, useState } from 'react'
import { api } from './api.js'
import { SessionCard } from './components/SessionCard.jsx'
import { GardenCard } from './components/GardenCard.jsx'
import { HeroBanner } from './components/HeroBanner.jsx'
import { GrowthGuide } from './components/GrowthGuide.jsx'

export default function App() {
  const [session, setSession] = useState(null)
  const [garden, setGarden] = useState(null)
  const [nudge, setNudge] = useState('')
  const [error, setError] = useState('')
  const [busy, setBusy] = useState(false)

  const refreshGarden = useCallback(async () => {
    try {
      setGarden(await api.getGarden())
    } catch (err) {
      showError(err.message)
    }
  }, [])

  useEffect(() => {
    ;(async () => {
      try {
        const current = await api.getCurrentSession()
        setSession(current)
        if (current) setNudge(current.nudge)
      } catch (err) {
        showError(err.message)
      }
      await refreshGarden()
    })()
  }, [refreshGarden])

  function showError(message) {
    setError(message)
    setTimeout(() => setError(''), 4000)
  }

  async function handleStart(minutes) {
    setBusy(true)
    try {
      const started = await api.startSession(minutes)
      setSession(started)
      setNudge(started.nudge)
    } catch (err) {
      showError(err.message)
    } finally {
      setBusy(false)
    }
  }

  async function handleComplete(id) {
    setBusy(true)
    try {
      const completed = await api.completeSession(id)
      setSession(null)
      setNudge(completed.nudge)
      await refreshGarden()
    } catch (err) {
      showError(err.message)
    } finally {
      setBusy(false)
    }
  }

  async function handleAbandon(id) {
    setBusy(true)
    try {
      const abandoned = await api.abandonSession(id)
      setSession(null)
      setNudge(abandoned.nudge)
    } catch (err) {
      showError(err.message)
    } finally {
      setBusy(false)
    }
  }

  return (
    <div className="min-h-screen bg-gray-100 text-gray-900">
      <div className="mx-auto max-w-[920px] px-5 pb-16 pt-8">
        <HeroBanner />

        <header className="mb-7 flex items-baseline justify-between border-b border-gray-200 pb-4">
          <h1 className="m-0 text-xl font-semibold tracking-tight">focus-flow</h1>
          <span className="text-[13px] text-gray-500">focus sessions · garden · streaks</span>
        </header>

        {error && (
          <div className="mb-4.5 rounded-lg bg-gray-800 px-3.5 py-2.5 text-[13px] text-white">
            {error}
          </div>
        )}

        <div className="grid grid-cols-1 gap-5 md:grid-cols-2">
          <SessionCard
            session={session}
            onStart={handleStart}
            onComplete={handleComplete}
            onAbandon={handleAbandon}
            nudge={nudge}
            busy={busy}
          />
          <GardenCard garden={garden} />
        </div>

        <GrowthGuide />

        <footer className="mt-7 text-center text-xs text-gray-400">
          served by Spring Boot on this origin · data resets on app restart (H2 in-memory)
        </footer>
      </div>
    </div>
  )
}
