<<<<<<< HEAD
import { Observable } from "rxjs"

/**
 * Renvoie un observable des changements de taille d'un élément HTML
 * @param e L'élément HTML dont on veut observer les changements de taille
 * @returns Un observable qui publie e à chaque fois que e change de taille
 */
export function getObsResize(e: HTMLElement): Observable<HTMLElement> {
    return new Observable<HTMLElement>(subscriber => {
        const resizeObs = new ResizeObserver(() => subscriber.next(e))
        resizeObs.observe(e)
    })
}
=======
import { Observable } from "rxjs"

/**
 * Renvoie un observable des changements de taille d'un élément HTML
 * @param e L'élément HTML dont on veut observer les changements de taille
 * @returns Un observable qui publie e à chaque fois que e change de taille
 */
export function getObsResize(e: HTMLElement): Observable<HTMLElement> {
    return new Observable<HTMLElement>(subscriber => {
        const resizeObs = new ResizeObserver(() => subscriber.next(e))
        resizeObs.observe(e)
    })
}
>>>>>>> 4d93b5eaad341077ba9d9163750d2e71240d4191
