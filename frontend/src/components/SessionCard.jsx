import { useState } from 'react'
import { useCountdown } from '../useCountdown.js'
import { playChime } from '../chime.js'

export function SessionCard({ session, onStart, onComplete, onAbandon, nudge, busy }) {
  const [minutes, setMinutes] = useState(25)
  const { text, overdue } = useCountdown(session, playChime)

  return (
    <section className="rounded-xl border border-gray-200 bg-white p-5">
      <h2 className="mb-4 text-xs font-semibold uppercase tracking-wider text-gray-500">
        Session
      </h2>

      {!session ? (
        <div className="flex gap-2.5">
          <input
            type="number"
            min={1}
            max={180}
            value={minutes}
            onChange={(e) => setMinutes(e.target.value)}
            className="flex-1 rounded-lg border border-gray-300 bg-gray-50 px-3 py-2.5 text-[15px] text-gray-900 focus:outline-none focus:ring-2 focus:ring-gray-400"
          />
          <button
            disabled={busy}
            onClick={() => onStart(parseInt(minutes, 10) || 25)}
            className="rounded-lg bg-gray-800 px-4 py-2.5 text-sm font-semibold text-white hover:bg-gray-700 disabled:opacity-50"
          >
            Start
          </button>
        </div>
      ) : (
        <div>
          <div className="py-3 pb-5 text-center">
            <div
              className={
                'text-[40px] font-bold tracking-tight tabular-nums ' +
                (overdue ? 'text-gray-600' : 'text-gray-900')
              }
            >
              {text}
            </div>
            <span
              className={
                'mt-1.5 inline-block rounded-full border border-gray-200 bg-gray-100 px-2.5 py-0.5 text-[11px] font-semibold uppercase tracking-wider text-gray-500 ' +
                (overdue ? 'animate-pulse' : '')
              }
            >
              {overdue ? "Time's up" : 'Running'}
            </span>
          </div>
          <div className="flex gap-2.5">
            <button
              disabled={busy}
              onClick={() => onComplete(session.id)}
              className="flex-1 rounded-lg bg-gray-800 px-4 py-2.5 text-sm font-semibold text-white hover:bg-gray-700 disabled:opacity-50"
            >
              Complete
            </button>
            <button
              disabled={busy}
              onClick={() => onAbandon(session.id)}
              className="flex-1 rounded-lg border border-gray-300 bg-gray-100 px-4 py-2.5 text-sm font-semibold text-gray-800 hover:bg-gray-200 disabled:opacity-50"
            >
              Abandon
            </button>
          </div>
        </div>
      )}

      <div className="mt-4 min-h-4 rounded-lg border border-dashed border-gray-300 bg-gray-50 px-3 py-2.5 text-[13px] text-gray-700">
        {nudge}
      </div>
    </section>
  )
}
